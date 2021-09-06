<template>
  <div id="app">
    <div id="nav" class="font-neodgm d-flex">
      <img src="@/assets/logo_with_cloud.png" alt="logo" id="logo" style="width:120px; margin:0 5% 0 5%">
      <div v-if="!isLogin" class="mt-2 fs-5">
        <router-link to="/home" style="margin:0 0 0 5%;" class="fs-6">Login</router-link> 
      </div>
      <div v-else-if="isLogin" class="mt-2 fs-5 d-flex">
        <router-link to="/town">Town</router-link> 
        <div style="position:absolute; right:5%;" class="d-flex">
          <p>오늘도 화이팅, {{ userid }}님!</p>
          <router-link :to="{name:'Profile', params:{userid:userid}}" class="mx-3 mb-3"> <el-button style="background-color:#8EBF24;" icon="el-icon-user" circle></el-button></router-link>
          <el-button @click="onLogout" icon="el-icon-switch-button" class="mb-3" type="warning" circle ></el-button>
        </div>
      </div>
    </div>

    <div v-if="isLogin && showUnity" v-show="buttonClick">
      <div  id="unity-container" class="unity-desktop" :class="{sidePosition:unityside}" style="margin:100px 0 0 0">
        <canvas id="unity-canvas" width=1120 height=630 @click="CanvasClick" ></canvas>
        <div id="unity-loading-bar">
          <div id="unity-logo"></div>
          <div id="unity-progress-bar-empty">
            <div id="unity-progress-bar-full"></div>
          </div>
        </div>
      </div>
    </div>
    <router-view/>
    <div id ="myname"></div>
    <div id ="yourname"></div>
    <div id="roomPk"></div>
    <to-main-modal  v-if="isModalView" @closemodal="isModalView = false" @movetomain="moveToMain"></to-main-modal>
    <to-board-modal  v-if="isBoardModalView" @closeModal="isBoardModalView = false"></to-board-modal>
    <to-timer-modal  v-if="isTimerModalView" @closeModal="isTimerModalView = false"></to-timer-modal>
    <fallin @webglon="runWebGL" :buttonClick="buttonClick" />
  </div>
</template>
<script>
import { mapState } from 'vuex'
import Main from './views/Main.vue'
import fallin from './components/fallin.vue'
import Home from './views/Home.vue'
import toMainModal from './components/GoMain.vue'
import toBoardModal from './components/Board/BoardModal.vue'
import toTimerModal from './components/TimerModal.vue'

export default {
  name: 'App',
  data () {
    return {
      isUnity:false,
      buttonClick:false,
      isModalView:false,
      isBoardModalView:false,
      isTimerModalView:false,
      townno:false,
      // isClicked:false,
    }
  },
  components:{
    Main,
    Home,
    toMainModal,
    toBoardModal,
    toTimerModal,
    fallin,
  },
  computed:{
    ...mapState(["isLogin","userid","unityInstance"]),
    showUnity : function(){
      if(this.$route.name === "Home" || this.$route.name === "Signup"){
        return false;
      }
      return true;
    },
    unityside : function(){
      if(this.$route.name==="Town"){
        return false;
      }
      return true;
    },
  },
  methods:{
    buttonToggle(){
      this.buttonClick = !this.buttonClick;
    },
    CanvasClick(){
      this.unityInstance.SendMessage("GameManager","FocusCanvas","1");
    },
    onLogout(){
      this.$store.commit('logout');
      this.$router.push({name:'Home'});
      location.reload();
    },
    toMainModal(){
      this.isModalView = true;
    },
    moveToMain(){
      this.$router.push({name:'Main'});
      console.log(this.unityInstance)
      this.isModalView = false;
      this.unityInstance.SendMessage("GameManager", "ChangeTableName", document.getElementById("yourname").value)
    },
    toBoardModal(){
      console.log('보드 보여');
      this.isBoardModalView = true;
    },
    toTimerModal(){
      console.log('타이머 보여');
      this.isTimerModalView = true;
    },
    runWebGL(){
      this.buttonClick++;
      var buildUrl = "Build";
      var loaderUrl = buildUrl + "/newbuild.loader.js";
      var config = {
        dataUrl: buildUrl + "/newbuild.data",
        frameworkUrl: buildUrl + "/newbuild.framework.js",
        codeUrl: buildUrl + "/newbuild.wasm",
        streamingAssetsUrl: "StreamingAssets",
        companyName: "DefaultCompany",
        productName: "Photon_Meta_Study",
        productVersion: "1.0",
      };

      var container = document.querySelector("#unity-container");
      var canvas = document.querySelector("#unity-canvas");
      var loadingBar = document.querySelector("#unity-loading-bar");
      var progressBarFull = document.querySelector("#unity-progress-bar-full");

      // By default Unity keeps WebGL canvas render target size matched with
      // the DOM size of the canvas element (scaled by window.devicePixelRatio)
      // Set this to false if you want to decouple this synchronization from
      // happening inside the engine, and you would instead like to size up
      // the canvas DOM size and WebGL render target sizes yourself.
      // config.matchWebGLToCanvasSize = false;

      if (/iPhone|iPad|iPod|Android/i.test(navigator.userAgent)) {
        container.className = "unity-mobile";
        // Avoid draining fillrate performance on mobile devices,
        // and default/override low DPI mode on mobile browsers.
        config.devicePixelRatio = 1;
        mobileWarning.style.display = "block";
        setTimeout(() => {
          mobileWarning.style.display = "none";
        }, 5000);
      } else {
        canvas.style.width = "1120px";
        canvas.style.height = "630px";
      }
      loadingBar.style.display = "block";

      var script = document.createElement("script");
      script.src = loaderUrl;
      script.onload = () => {
        createUnityInstance(canvas, config, (progress) => {
          progressBarFull.style.width = 100 * progress + "%";
        }).then((unityInstance) => {
          this.$store.commit("getUnityIn",unityInstance);
          loadingBar.style.display = "none";
        }).catch((message) => {
          alert(message);
        });
      };
      document.body.appendChild(script);
        var canvas = document.querySelector("#unity-canvas");
        if (this.$route.name !== "Town") {
          canvas.style.width = "300px";
          canvas.style.height = "300px";
        }
        else {
          canvas.style.width = "1120px";
          canvas.style.height = "630px";
        }
    }
    
  },
  
}

</script>
<style>
@import url('//cdn.jsdelivr.net/gh/Dalgona/neodgm-webfont@1.521/neodgm/style.css');
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-image: url("assets/background-image.png");
  background-size: cover;
  background-attachment: fixed;
  overflow:auto;
  height:100vh;
}

.font-neodgm { font-family: 'NeoDunggeunmo'; }


#nav {
  position: relative;
  padding: 10px;
  background: #f2e9e1;
}

#nav a {
  font-weight: bold;
  color: #1c140d;
}

#nav a.router-link-exact-active {
  color: #1c140d;
}

.sidePosition {
  position:fixed;
  bottom:0;
  margin-top: 15%;
  margin-left: 30%;
  padding-auto: 15%;
}

.originalPosition {
  margin-top: 5%;
}

</style>
