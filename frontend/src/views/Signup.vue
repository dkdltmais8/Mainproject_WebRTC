<template>
  <div class="container font-neodgm mt-3">
    <div class="d-flex flex-column align-items-center signupbox">
      <img src="@/assets/signup.png" alt="signup" style="width:200px;" class="ms-4">
      <h4 class="my-2 ms-5">메타스터디에 오신 것을 환영합니다!</h4>
      <el-form :model="SignupForm" :rules="rules" label-width="120px" id="formstyle" ref="SignupForm" class="mt-1">
          <el-form-item label="아이디" prop="userid">
          <div class="d-flex">
            <el-input v-model="SignupForm.userid" autocomplete="off"></el-input>
            <el-button class="ms-4" type="warning" @click="onIdCheck">아이디 중복확인</el-button>
          </div>
          </el-form-item>
        <el-form-item label="이름" prop="username">
          <el-input v-model="SignupForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="비밀번호" prop="password">
          <el-input v-model="SignupForm.password" autocomplete="off" type="password"></el-input>
        </el-form-item>
        <el-form-item label="비밀번호확인" prop="passwordconfirmation">
          <el-input v-model="SignupForm.passwordconfirmation" autocomplete="off" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="onSignup('SignupForm')" style="background-color:#8EBF24; color:white;" class="me-5">회원가입</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog
      title="아이디 중복 검사" width="30%" :visible.sync="modalVisible">
      <span>{{ msg }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="modalVisible = false" type="warning">닫기</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "Home",
  data(){
    var checkpassword = (rule,value,callback) => {
      if (!value) {
        return callback(new Error('비밀번호를 입력하세요'))
      }
      if (value !== this.SignupForm.password){
        return callback(new Error('비밀번호가 다릅니다.'))
      }
      else{
        callback()
      }
    }
    return {
      SignupForm:{
        userid:undefined,
        password:null,
        username:null,
        passwordconfirmation:null,
        idCheck:false,
      },
      modalVisible:false,
      msg: undefined,
      rules: {
        userid:[
          { required:true, message:"아이디를 입력해주세요!", trigger:"blur"},
        ],
        password:[
          { required:true, message:"비밀번호를 입력해주세요", trigger:"blur"},
          { pattern: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&^])[A-Za-z\d$@$!%*#?&^]{9,16}$/, message:'비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.', trigger:'blur' },
        ],
        username:[
          { required:true, message:"이름을 입력해주세요", trigger:"blur"}
        ],
        passwordconfirmation:[
          { validator: checkpassword, trigger:'blur'},
          { required:true, message:"비밀번호를 다시한번 입력해주세요", trigger:"blur"}
        ],
      },
    }
  },
  methods:{
    onSignup(formName){
      this.$refs[formName].validate((valid) => {
        if (valid && this.idCheck) {
          this.$store.dispatch('requestSignup',{'password':this.SignupForm.password,'userid':this.SignupForm.userid,'username':this.SignupForm.username})
          .then(res=>{
            console.log(res,'회원가입')
            this.$router.push({name:'Home'})
          })
          .catch(err=>{
            console.log(err)
          })
        }
        else{
          alert('아이디 중복검사를 해주세요!')
        }
      })
    },
    onIdCheck(){
      if (this.SignupForm.userid !== undefined){
        this.$store.dispatch('IdCheck',{'userId':this.SignupForm.userid})
        .then(res=>{
          console.log(res)
          this.idCheck = true
          this.modalVisible = true
          this.msg = "이 아이디는 사용이 가능합니다."
        })
        .catch(err=>{
          console.log(err)
          this.modalVisible = true
          this.msg = "이 아이디는 이미 사용중입니다"
        })
      }
    },
  }
}
</script>
<style>
.signupbox{
  background-color: white;
}
</style>

