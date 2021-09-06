<template>
  <div>
    <div class="overlay font-neodgm" @click="$emit('CloseModal')" id='modal-4'></div>
    <div class="modal-card" @click="DisableCanvas">
    <h1>게시물 수정</h1>
    <div class="container"  @click="DisableCanvas">
      <div class="row">
        <div class="col-1"></div>
        <div class="col-10 bg-light p-5" align="left">
          <el-form align="left">
            <el-form-item label="제목"></el-form-item>
            <el-input maxlength='20' v-model="form.title" value="review_title" placeholder="Enter title" required />
            <hr>
            <el-form-item label="내용"></el-form-item>
            <el-input type=textarea v-model="form.content" value="review_content" placeholder="Enter something..." rows="15" max-rows="10" required />
            <p></p>
            <el-button @click="onBack">취소</el-button>
            <el-button @click = "onSubmit" type="submit" style="background-color:blue"><span style="color:white">확인</span></el-button>
          </el-form>
          </div>
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
  name: 'Boardupdate',
  props:['review_Pk','unityInstance','review_title','review_content'],
  data: function () {
    return {
      review: {},
      form: {
        title: '',
        content: '',
       }
    }
  },
  computed:{
    ...mapState(["userid","unityInstance"]),
  },
  methods: {
    DisableCanvas(){
            console.log("아 유니티 꺼졋다");
            this.unityInstance.SendMessage("GameManager","FocusCanvas","0");
        },
    onSubmit: function () {
      axios({
        url: `/room/board/${this.review_Pk}/`,
        method: 'PUT',
        data: this.form
      })
      .then((res)=>{
        this.$emit('closeModal')
      })
    },
    getReview: function () {
      // axios({
      //   url: `/room/board/${this.review_Pk}/`,
      //   method: 'get',
      // })
      // .then((response) => {
      //   console.log(response)
      //   this.review = response.data
      //   this.form.title = this.review.title
      //   this.form.content = this.review.content
      // })
    },
    onBack: function () {
      this.$emit('closeModal')
    }
  },
  created: function () {
    this.form.title = this.review_title
    this.form.content = this.review_content 

  }
}
</script>

<style scoped>

button {
  float: right;
  margin: 10px;
}
</style>