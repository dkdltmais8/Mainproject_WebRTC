<template>
  <div v-if="!buttonClick && isLogin && isright" class="container mt-4 d-flex flex-column align-items-center" style="background-color:white;">
    <img src="@/assets/logo_with_cloud.png" alt="" class="mt-3">
    <el-button @click="$emit('webglon')" class="mb-3" style="border:none;"><img src="@/assets/GetIn.png" alt=""></el-button>
  </div>
</template>

<script>
import {mapState} from 'vuex'
export default {
  props:['buttonClick'],
  // data(){
  //   return{
  //     isright:true,
  //   }
  // },
  computed:{
    ...mapState(["isLogin"]),
    isright(){
      if (this.$route.name == 'Intro' || this.$route.name == 'Profile'){
        return false;
      }
      else{
        return true;
      }
    },
  },

}
</script>

<style>

</style>