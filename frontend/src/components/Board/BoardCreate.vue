<template>
<div class="font-neodgm">
  <div class="overlay font-neodgm" @click="$emit('closeModal')" id="modal-2"></div>
  <div class="modal-card" @click="DisableCanvas">
  <h1 class="mt-3">게시물 작성</h1>
    <div class="row">
      <div class="col-1"></div>
      <div class="col-10 bg-light p-5" align="left">
        <el-form align="left" enctype="multipart/form-data" >
          <el-form-item label="제목"></el-form-item>
          <el-input maxlength='20' v-model="form.title" placeholder="제목을 입력해주세요" required />
          <hr>
          <el-form-item label="내용"></el-form-item>
          <el-input type="textarea" v-model="form.content" placeholder="내용을 입력해주세요" rows="15" max-rows="10" required />
          <p></p>
          <el-button @click="$emit('closeModal')" type="warning">취소</el-button>
          <el-button  style="background-color:#8EBF24; color:white;" type="submit" @click="boardWrite('form')">작성</el-button>
          <!-- <input type="file" multiple="multiple" ref="uploadImage" @change="fileUpload"> -->
        </el-form>
      </div>
      <div class="col-1"></div>
    </div>
  </div>
</div>
</template>

<script>
import axios from '../../js/axios'
import { mapState } from 'vuex'
export default {
  
  name: 'BoardCreate',
  data: function () {
    return {
      form: {
        title: '',
        content: '',
      },
      user_pk:'',
      room_pk:'',
      image:'',
      board_pk:'',
      
    }
  },
  computed:{
    ...mapState(["userid","unityInstance"]),
  },
  methods: {
    // fileUpload(event){
    //   this.image = this.$refs.uploadImage;
    // },
    onback(){
      this.$emit('closeModal');
    },
    getroompk(){
        this.room_pk = document.getElementById('roomPk').value
    },
    getuserpk(){
      axios.get("/users/me", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('accessToken')}`
        }
      })
      .then((res)=>{
        console.log(res)
        this.user_pk = res.data.userPk;
      })
    },
    boardWrite(){
      this.$store.dispatch('requestWrite',{'title':this.form.title,'content':this.form.content,'room_pk':this.room_pk,'user_pk':this.user_pk})
      .then(res=>{
        this.$emit('closeModal')
      })
    },
    DisableCanvas(){
			// console.log("아 유니티 꺼졋다");
			this.unityInstance.SendMessage("GameManager","FocusCanvas","0");
		},
  },
  created(){
    this.getroompk()
    this.getuserpk()
  },
  mounted() {
    var canvas = document.querySelector("#unity-canvas");
    console.log('이건 BoardDetail')
    if (this.$route.name !== "Town") {
      canvas.style.width = "300px";
      canvas.style.height = "300px";
    }
    else {
      canvas.style.width = "1120px";
      canvas.style.height = "630px";
    }
  }
}
</script>

<style scoped>

button {
  float: right;
  margin: 10px;
}
.container {
  position: sticky;
}
</style>