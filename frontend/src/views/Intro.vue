<template>
  <div class="container font-neodgm mt-3" style="background-color:white;">
    <div class="d-flex flex-column align-items-center">
      <img src="@/assets/logo_with_cloud.png" alt="" >
      <!-- 메타스터디 안내문구 -->
      <div class="my-5 d-flex" >
        <img src="@/assets/Intro/coder.png" style="width:20%" class="col-3 ms-3">
        <div class="col-9 d-flex flex-column mt-3 ">
          <h2 class="ms-4">미래의 개발자분들, 메타스터디에 오신걸 환영합니다!</h2>
          <span class="fs-5 mt-3" > 저희 메타스터디는 메타버스와 화상채팅 기능을 결합한, 오직 개발자분들을 위한 스터디 플랫폼입니다! <br>
            스터디원들과 교류하는 느낌이 나지않는, "고독한 스터디"에서 벗어나고 싶으시다고요? <br>
            그렇다면, 메타스터디가 바로 당신을 위한 플랫폼입니다!
          </span>
        </div>
      </div>
      <!-- 작동법 -->
      <!-- 화상 채팅법 -->
      <div class="d-flex my-5 ">
        <div class="d-flex flex-column">
          <h2 class="mx-3">맵에서 스터디원에게 화상채팅을 걸어주세요!</h2>
          <span class="fs-5 mt-3">대화하고 싶은 스터디원에게 다가가, spacebar를 누르면 화상 연결!</span>
          <img src="@/assets/Intro/spacebar.png" style="width:30%; margin:0 0 0 30%" alt="">
        </div>
        <img src="@/assets/Intro/chatexam.png" alt="" style="width:45%;">
      </div>
      <!-- 알고리즘 사이트 이동법 -->
      <div class="d-flex my-5 ">
        <img src="@/assets/Intro/algoexam.jpg" alt="" style="width:40%;">
        <div class="d-flex flex-column">
          <h2 class="mx-3">원하는 알고리즘 사이트를 손쉽게 이동하세요!</h2>
          <span class="fs-5 mt-3">유명한 알고리즘 사이트들을 "C"버튼 하나로 이동해보세요!</span>
          <img src="@/assets/Intro/cbutton.png" style="width:30%; margin:0 0 0 50%" alt="">
        </div>
      </div>
      <div class="d-flex flex-column mt-5">
        <h1>더 귀엽고, 더 많은 기능들이 META STUDY안에 있습니다!</h1>
        <span class="mt-3 fs-5">더이상, 지루한 스터디는 그만! 지금 META STUDY와 함께 개발의 늪에 빠져보세요~</span>
        <el-button style="border:none;" @click="moveRegister"><img src="@/assets/signup.png" alt="" style="width:300px;"></el-button>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name:'Intro',
  methods:{
    moveRegister(){
      this.$router.push({name:'Signup'})
    }
  }
}
</script>

<style>
</style>
