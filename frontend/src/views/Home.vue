<template>
  <div class="container font-neodgm">
    <el-alert title="로그인 실패!" type="error" description="비밀번호가 일치하지 않습니다!" v-if="failLogin" show-icon>
    </el-alert>
    <div class="d-flex flex-column align-items-center mt-5 border loginbox" style="align-center" @click="downalert">
      <img src="@/assets/logo_with_cloud.png" alt="" class="ms-5">
      <el-form :model="LoginForm" :rules="rules" label-width="120px" id="formstyle" ref="LoginForm">
        <el-form-item label="아이디" prop="userid" class="mt-4">
          <el-input v-model="LoginForm.userid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="비밀번호" prop="password">
          <el-input v-model="LoginForm.password" autocomplete="off" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="onLogin('LoginForm')" style="background-color:#8EBF24; color:white;" class="me-4">로그인</el-button>
        </el-form-item>
      </el-form>
      <p class="ms-5">아직 회원이 아니신가요?<el-button type="warning" @click="goRegister" class="ms-3" size="small">회원가입</el-button></p>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  data(){
    return {
      LoginForm:{
        userid:'',
        password:'',
      },
      rules: {
        userid:[
          { required:true, message:"아이디를 입력해주세요!", trigger:"blur"},
        ],
        password:[
          { required:true, message:"비밀번호를 입력해주세요", trigger:"blur"},
          { pattern: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&^])[A-Za-z\d$@$!%*#?&^]{9,16}$/, message:'비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.', trigger:'blur' },
        ]
      },
      failLogin:false,
    }
  },
  methods:{
    onLogin(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store.dispatch('requestLogin',{'password':this.LoginForm.password,'userid':this.LoginForm.userid})
          .then(res=>{
            console.log(res)
            localStorage.setItem('accessToken',res.data.accessToken)
            //유저정보가저오는 요청
            this.$store.dispatch('getUserInfo',res.data.accessToken)
            // document.getElementById("unitybase").style.display="block"
            this.$router.push({name:'Town'})
           
            
      })
          .catch(err=>{
            this.failLogin = true;
      })
        }
        else {
          console.log('입력 정보를 확인해주세요')
          return false
        }
      })
    },
    goRegister(){
      this.$router.push({name:'Signup'})
    },
    downalert(){
      this.failLogin = false;
    },

  }
};
</script>
<style>
  #formstyle{
    width:50%;
  }
  .loginbox{
    background-color:white;
  }
  .blackfont{
    color:black;
  }
</style>
