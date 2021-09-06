<template>
  <div>
    <updatemodal v-if="showUpdate" @closeModal="reUpdate()" :review_Pk="review_pk" :review_title="review.title" :review_content="review.content"></updatemodal>
    <div class ="font-neodgm" v-if="!showUpdate">
      <div class="overlay" @click="$emit('CloseModal')" id ="modal-3"></div>
      <div class="modal-card mt-3">
      <h1 class="mt-3">{{ review.title }}</h1>
    <div class="container">
      <div class="row">
        <div class="col-1"></div>
        <div class="col-10 bg-light p-1" align="left">
          <hr>
          <div class="left">작성자 : {{ review.user.userid }}</div>
          <div align="right">{{ review.created }}</div>
          <hr>
          <div class="min-h mt-2 fs-4" >{{ review.content }}</div>
          <hr>
          <el-button v-show="itsme" class="mt-2" @click="onUpdate" style="background-color:#8EBF24; color:white;">수정하기</el-button>
          <el-button v-show="itsme" @click="onDelete" type="warning">글 삭제</el-button>
          <el-button class="right mx-1 mt-2" @click="back" style="background-color:#6AAFE6; color:white;" >돌아가기</el-button>
        </div>
      </div>
        <div class="col-1"></div>
      </div>
    </div>
    </div>
  </div>

</template>

<script>
import axios from '../../js/axios'
import updatemodal from './Boardupdate.vue'
export default {
  name: 'BoardDetailmodal',
  components:{
    updatemodal
  },
  props:[
    'review_pk',
    'room_pk'
  ],
  data: function () {
    return {
      userid:'',
      iswriter: false,
      showUpdate:false,
      review: {user:{userid:''}},
      
    }
  },
  computed: {
    itsme() {
      if(this.review.user.userid !== this.userid ){
        return false
      }
      return true
    }
  },
  methods: {
    onUpdate: function () {
      this.showUpdate = true
    },
    back: function () {
      this.$emit('closeModal')
    },
    onDelete(){
      axios({
        url: `/room/board/${this.review_pk}/`,
        method: 'delete',
      })
      .then(res=>{
        this.$emit('closeModal')
      })
    },
    reUpdate(){
      // axios({
      //   url:`/room/board/${this.review_pk}/`,
      //   method:"GET",
      // })
      // .then(res=>{
      //   console.log(res)
      //   this.review= res.data
      //   this.showUpdate=false
      // })
      this.$emit('closeModal')
    }
  },
  created: function () {
    console.log('시작')
    axios({
        url:`/room/board/${this.review_pk}/`,
        method:"GET",
      })
      .then(res=>{
        console.log(res)
        this.review= res.data
      })
    axios({
      url:'/users/me',
      method:'get',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('accessToken')}`
        }
    })
    .then(res=>{
      console.log(res)
      this.userid = res.data.userId;
    })
  },
 
}
</script>

<style scoped>

.left {
  float: left;
}
.right {
  float: right;
}
.min-h {
  min-height: 400px;
  padding: 10px 0 20px 0;
}
.width {
  left: 0px;
  width: 85%;
  height: 40px;
  margin: 20px 20px 20px 0;
}
ul {
  list-style: none;
  width: 92%;
}
li {
  height: 20px;
}

</style>