<template>
    <div>
        <to-town-modal  v-if="isModalView" @closeModal="isModalView = false" @moveToTown="moveToTown"></to-town-modal>
        <div id="main-container" class="container d-flex mainbox font-neodgm pb-3 justify-content-center" @click="DisableCanvas">
            <!-- 화상회면 용  -->
            <div id="session" v-if="session" class="col-9">
                <div id="session-header" class="mt-3 font-neodgm d-flex flex-column align-items-center" style="width:100%;">
                    <h4>환경 설정</h4>
                    <div class="d-flex justify-content-center p-4">
                        <el-button  v-if="audioStatus==1" @click="audioOnOff" style="border-color:#8EBF24;" circle><img src="@/assets/micon.png" alt="" style="width:30px;"></el-button>
                        <el-button  v-else @click="audioOnOff" style="border-color:red" circle><img src="@/assets/micoff.png" alt="" style="width:30px;"></el-button>
                        <el-button  v-if="cameraStatus==1" @click="cameraOnOff" style="border-color:#8EBF24;" circle><img src="@/assets/cameraon.png" alt="" style="width:30px;"></el-button>
                        <el-button  v-else @click="cameraOnOff" style="border-color:red" circle><img src="@/assets/cameraoff.png" alt="" style="width:30px;"></el-button>
                        <el-button   @click="share" style="border-color:#8EBF24;" circle><img src="@/assets/share.png" alt="" style="width:30px;"></el-button>
                        <el-button @click="ChatControl" style="border-color:#8EBF24;" circle ><i class="el-icon-chat-dot-round mx-2"></i></el-button>
                        <el-button   @click="toTownModal" style="border-color:red;" circle><img src="@/assets/logout.png" alt="" class="ms-1" style="width:25px;"></el-button>
                    </div>
                </div>
                <div class="d-flex flex-column mt-5 align-items-center">
                    <!-- 카메라 담는용 -->
                    <div id="main-video" class="col-12 ms-3">
                        <user-video :stream-manager="mainStreamManager"/>
                    </div>
                    <div id="video-container" class="d-flex mb-2 mx-0 col-12" >
                        <user-video :stream-manager="now" @click.native="updateMainVideoStreamManager(now)" style="border:black;" class="col-2"/>
                        <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"  style="border:black;" class="col-2"/>
                    </div>
                </div>
            </div>
            <!-- 채팅용 -->
            <div v-if="ChatOn" style="border-left:2px solid #D3D3D3;" @click="DisableCanvas" class="col-3 mt-3 ps-2">
                <form @submit.prevent>
                    <label for="message">메세지를 입력하세요</label>
                    <input type="text" v-model="ChatForm.message" @keyup.enter="sendMessage" ><el-button @click="sendMessage" type="warning" style="width:25%; height:10%" class="ms-1 p-2">보내기</el-button>
                </form>
                <message :messageList="messageList" class="mt-2"/>

            </div>
        </div>
    </div>
</template>
<script>
import { mapState } from 'vuex'
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '../views/UserVideo.vue';
import toTownModal from '../components/GoTown.vue'
import Message from '../components/Message.vue'

axios.defaults.headers.post['Content-Type'] = 'application/json';
console.log(location.hostname)
const OPENVIDU_SERVER_URL = "https://i5b105.p.ssafy.io:8443"
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
    name:'Main',
  computed:{
    ...mapState(["userid","unityInstance"]),
  },
    name: 'App',

    components: {
        UserVideo,
        toTownModal,
        Message,
    },

    data () {
        return {
            isModalView:false,
            OV: undefined,
            session: undefined,
            mainStreamManager: undefined,
            publisher: undefined,
            now : undefined,
            sharepublisher: undefined,
            subscribers: [],
            minisubs:[],
            messageList:[],
            ChatOn:false,
            ChatForm:{
                message:'',
            },
            cameraStatus:'1',
            audioStatus:'1',
            // mySessionId: 'SessionA',
            // myUserName: 'Participant' + Math.floor(Math.random() * 100),
            mySessionId: document.getElementById("yourname").value,
            myUserName: document.getElementById("myname").value,
        }
    },

    methods: {
        ChatControl(){
            if (this.ChatOn) {
                this.ChatOn = false;
            }
            else {
                this.ChatOn = true;
            }
        },
        toTownModal(){
            this.isModalView = true;
        },
        moveToTown(){
            this.isModalView = false;
            setTimeout(() => {
                this.leaveSession();
            }, 1000);
            this.$router.push({name:'Town'});
        },
        joinSession () {
            console.log(this.mySessionId);
            console.log(this.myUserName);
            console.log(this.session);
            console.log("★★★★★★");

            // --- Get an OpenVidu object ---
            this.OV = new OpenVidu();

            // --- Init a session ---
            this.session = this.OV.initSession();

            // --- Specify the actions when events take place in the session --

            // On every new Stream received...
            this.session.on('streamCreated', ({ stream }) => {
                const subscriber = this.session.subscribe(stream);
                this.subscribers.push(subscriber);
            });

            // On every Stream destroyed...
            this.session.on('streamDestroyed', ({ stream }) => {
                const index = this.subscribers.indexOf(stream.streamManager, 0);
                if (index >= 0) {
                    this.subscribers.splice(index, 1);
                }
            });

            // On every asynchronous exception...
            this.session.on('exception', ({ exception }) => {
                console.warn(exception);
            });

            this.session.on('signal:my-chat', (event) => {
                console.log(event.data); // Message
                // console.log(event.from.data.clientData); // Connection object of the sender
                 // Connection object of the sender
                const Sender = JSON.parse(event.from.data);
                console.log(Sender.clientData);
                // console.log(event.type); // The type of message ("my-chat")
                this.messageList.push([Sender.clientData, event.data]);
            })

            // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
            // 'token' parameter should be retrieved and returned by your own backend
            this.getToken(this.mySessionId).then(token => {
                this.session.connect(token, { clientData: this.myUserName })
                    .then(() => {

                        // --- Get your own camera stream with the desired properties ---
                        this.makevideopublisher();
                        // --- Publish your stream ---
                        this.session.publish(this.publisher);
                        this.now = this.publisher;


                    })
                    .catch(error => {
                        console.log('There was an error connecting to the session:', error.code, error.message);
                    });
            });

            window.addEventListener('beforeunload', this.leaveSession)
        },
        sendMessage (){
            if (this.mySessionId != null){
                this.session.signal({
                data: this.ChatForm.message,  // Any string (optional)
                to: [],                     // Array of Connection objects (optional. Broadcast to everyone if empty)
                type: 'my-chat'             // The type of message (optional)
                })
                .then(() => {
                    this.ChatForm.message = ''
                    console.log('Message successfully sent');
                })
                .catch(error => {
                    console.error(error);
                });
            }
            else
            {
                console.log("회원정보가 없습니다!")
            }
        },
        leaveSession () {
            // --- Leave the session by calling 'disconnect' method over the Session object ---
            if (this.session) this.session.disconnect();
                console.log("떠나가");
                this.session = undefined;
                this.mainStreamManager = undefined;
                this.publisher = undefined;
                this.subscribers = [];
                this.OV = undefined;
                this.now = undefined;
                this.sharepublisher = undefined;

            window.removeEventListener('beforeunload', this.leaveSession);
        },

        updateMainVideoStreamManager (stream) {
            if (this.mainStreamManager === stream) return;
            // main-video 삭제 후, 재생성
            const a = document.getElementById("main-video").firstChild
            if (a != null) a.remove()
            this.mainStreamManager = stream;
            this.mainStreamManager.createVideoElement("main-video", 'APPEND');
            document.getElementById("main-video").style.height = "730px"
            document.getElementById("main-video").firstChild.style.height = "100%"
        },
        DisableCanvas(){
            console.log("아 유니티 꺼졋다");
            this.unityInstance.SendMessage("GameManager","FocusCanvas","0");
        },

        /**
         * --------------------------
         * SERVER-SIDE RESPONSIBILITY
         * --------------------------
         * These methods retrieve the mandatory user token from OpenVidu Server.
         * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
         * the API REST, openvidu-java-client or openvidu-node-client):
         *   1) Initialize a Session in OpenVidu Server    (POST /openvidu/api/sessions)
         *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
         *   3) The Connection.token must be consumed in Session.connect() method
         */

        getToken (mySessionId) {
            return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
        },

        // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
        createSession (sessionId) {
            return new Promise((resolve, reject) => {
                axios
                    .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
                        customSessionId: sessionId,
                    }), {
                        auth: {
                            username: 'OPENVIDUAPP',
                            password: OPENVIDU_SERVER_SECRET,
                        },
                    })
                    .then(response => response.data)
                    .then(data => resolve(data.id))
                    .catch(error => {
                        if (error.response.status === 409) {
                            resolve(sessionId);
                        } else {
                            console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
                            if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
                                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
                            }
                            reject(error.response);
                        }
                    });
            });
        },

        // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
        createToken (sessionId) {
            return new Promise((resolve, reject) => {
                axios
                    .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
                        auth: {
                            username: 'OPENVIDUAPP',
                            password: OPENVIDU_SERVER_SECRET,
                        },
                    })
                    .then(response => response.data)
                    .then(data => resolve(data.token))
                    .catch(error => reject(error.response));
            });
        },
        cameraOnOff()
        {
            console.log("카메라 끄기/키기");
            console.log(this.cameraStatus);
            if(this.cameraStatus == '1')
            {
                this.now.publishVideo(false);
                this.cameraStatus ='2';
            }
            else
            {
                this.now.publishVideo(true);
                this.cameraStatus = '1';
            }
            
        },
        audioOnOff()
        {
            if(this.audioStatus =='1')
            {
                this.now.publishAudio(false);
                this.audioStatus ='2';
            }
            else{
                this.now.publishAudio(true);
                this.audioStatus ='1';
            }
        },
        makevideopublisher() {
            this.publisher = this.OV.initPublisher(undefined, {
            audioSource: undefined, // The source of audio. If undefined default microphone
            videoSource: undefined, // The source of video. If undefined default webcam
            publishAudio: this.audioStatus == "1" ? true : false, // Whether you want to start publishing with your audio unmuted or not
            publishVideo: true, // Whether you want to start publishing with your video enabled or not
            resolution: "640x480", // The resolution of your video
            frameRate: 30, // The frame rate of your video
            insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
            mirror: false, // Whether to mirror your local video or not
            });
            this.mainStreamManager = this.publisher;
            this.session.publish(this.publisher)
        },

        share() {
            if (this.now == this.sharepublisher) {
                alert("이미 공유중입니다")
            }
            else{
                this.sharepublisher = this.OV.initPublisher("", {
                    videoSource: "screen",
                    publishAudio: this.audioStatus == "1" ? true : false,
                });
                this.sharepublisher.once("accessAllowed", (event) => {     
                    this.sharepublisher.stream.getMediaStream().getVideoTracks()[0].addEventListener("ended", () => {
                        console.log('User pressed the "Stop sharing" button');
                        this.tochange(this.sharepublisher, this.publisher);
                    });
                    // 공유 화면 켜주고 카메라, 오디오 세팅 그대로 맞춰주기
                    this.tochange(this.publisher, this.sharepublisher)
                });
                this.sharepublisher.once("accessDenied", (event) => {
                    console.warn("ScreenShare: Access Denied");
                });
            }
        },
        tochange(publi1, publi2){
            this.foo(publi1)
            console.log('1')
            this.foo2(publi2)
            console.log('2')
            this.now = publi2
            console.log('3')
            this.updateMainVideoStreamManager(this.now)
            console.log('4')
        },
        foo(publi1){
            return this.session.unpublish(publi1)
        },
        foo2(publi2){
            return this.session.publish(publi2)
        },
    },
    created(){
        this.joinSession();
        if (!this.unityInstance){
            this.$router.push({name:'Town'});
        }
    },
    mounted() {
        var canvas = document.querySelector("#unity-canvas");
        console.log('main')
        if (this.$route.name !== "Town") {
            canvas.style.width = "300px";
            canvas.style.height = "300px";
        }
        else {
            canvas.style.width = "1120px";
            canvas.style.height = "630px";
        }

    },

}
</script>
<style>
@import url('//cdn.jsdelivr.net/gh/Dalgona/neodgm-webfont@1.521/neodgm/style.css');
.font-neodgm { font-family: 'NeoDunggeunmo'; }
.mainbox{
    background-color: white;
}

</style>