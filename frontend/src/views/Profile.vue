<template>
  <div class="font-neodgm d-flex flex-column align-items-center" @click="DisableCanvas">
    <div class="profile mt-4 pb-5">
      <h1 class="mt-3">{{ username }}님, 안녕하세요!</h1>
      <img src="@/assets/edit.png" style="width:200px" alt=""  @click="dialogVisible = true">
      <el-dialog title="회원정보 수정" :visible.sync="dialogVisible" width="60%" @click="DisableCanvas">
        <div class="d-flex flex-column ms-5" style="width:100%">
          <el-form :model="UpdateForm" :rules="rules" label-width="150px" id="formstyle" ref="UpdateForm">
            <el-form-item label="이름" prop="username" style="width:150%">
              <el-input v-model="UpdateForm.username" autocomplete="off" @click="DisableCanvas"></el-input>
            </el-form-item>
            <el-form-item label="비밀번호" prop="password" style="width:150%">
              <el-input v-model="UpdateForm.password" autocomplete="off" type="password"></el-input>
            </el-form-item>
            <el-form-item label="비밀번호확인" prop="passwordconfirmation" style="width:150%">
              <el-input v-model="UpdateForm.passwordconfirmation" autocomplete="off" type="password"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">닫기</el-button>
            <el-button @click="onUpdate('UpdateForm')" style="background-color:#8EBF24; color:white;">회원 정보 수정</el-button>
            <el-button type="danger" @click="onDelete">회원 탈퇴</el-button>
          </span>
        </div>
      </el-dialog>
       <!--회원탈퇴용  -->
       <el-dialog
        title="정말 탈퇴하시겠어요?"
        :visible.sync="deletedialog"
        width="60%"
        center>
        <span><img src="@/assets/leaving.png" alt="" style="width:300px; margin:0 0 0 35%"></span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="deletedialog = false">닫기</el-button>
          <el-button type="danger" @click="DeleteUser">탈퇴하기</el-button>
        </span>
      </el-dialog>

    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name:'Profile',
  data(){
    var checkmyPassword = (rule,value,callback) => {
      if (!value) {
        return callback(new Error('비밀번호를 입력하세요'))
      }
      if (value !== this.UpdateForm.password){
        return callback(new Error('비밀번호가 다릅니다.'))
      }
      else{
        callback()
      }
    }
    return {
      UpdateForm:{
        username:'',
        passwordconfirmation:null,
        password:null,
      },
      userid:'',
      dialogVisible:false,
      deletedialog:false,
      rules: {
        password:[
          { required:true, message:"비밀번호를 입력해주세요", trigger:"blur"},
          { pattern: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&^])[A-Za-z\d$@$!%*#?&^]{9,16}$/, message:'비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.', trigger:'blur' },
        ],
        username:[
          { required:true, message:"이름을 입력해주세요", trigger:"blur"}
        ],
        passwordconfirmation:[
          { validator: checkmyPassword, trigger:'blur'},
          {required:true, message:"비밀번호를 입력해주세요", trigger:"blur"}
        ],
      },
    }
  
  },
  computed:{
    ...mapState(["unityInstance","username"])
  },
  created(){
    this.$store.dispatch('getThisUser',{userid:this.$route.params.userid})
    .then(res=>{
      this.UpdateForm.username = res.data.username
      this.UpdateForm.usernickname = res.data.usernickname
      this.userid = this.$route.params.userid
    })
    .catch(err=>{
      console.log(err)
    })

    if (this.$route.params.userid !== this.$store.state.userid) {
      console.log(this.$route.params.userid+"userid");
      console.log(this.$store.state.userid+"회원로그인");
      // this.$router.push({name:'Town'});
    }
  },
  mounted() {
    var canvas = document.querySelector("#unity-canvas");
    if (this.$route.name !== "Town") {
      canvas.style.width = "300px";
      canvas.style.height = "300px";
    }
    else {
      canvas.style.width = "1120px";
      canvas.style.height = "630px";
    }
  },
  methods:{
    onUpdate(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store.dispatch('requestUpdate',{'data':{'password':this.UpdateForm.password,'username':this.UpdateForm.username, 'usernickname':this.UpdateForm.usernickname},'userid':this.userid})
          .then(res=>{
            this.$store.dispatch('getThisUser',{userid:this.$route.params.userid})
            location.reload()
            console.log(res)
            this.dialogVisible = false
          })
          .catch(err=>{
            console.log(err)
          })
        }
        else{
          console.log('입력값을 확인하세요')
        }
      })
    },
    DeleteUser(){
      this.$store.dispatch('deleteUser',{'userid':this.$route.params.userid,})
      .then(res=>{
        console.log(res)
        localStorage.removeItem('accessToken')
        this.$router.push({name:'Home'})
        location.reload()

      })
      .catch(err=>{
        console.log(err)
      })
    },
    onDelete(){
      this.dialogVisible = false;
      this.deletedialog = true;
    },
    DisableCanvas(){
			console.log("아 유니티 꺼졋다");
			this.unityInstance.SendMessage("GameManager","FocusCanvas","0");
		},
  }
}
</script>
<style>
  .profile{
    background-color: white;
    width:60%;
  }
</style>

