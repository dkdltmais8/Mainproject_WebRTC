<template>
    <div>
        <create-modal v-if="showCreate" @closeModal="restart()" > </create-modal>
        <detail-modal v-if="showDetail" @closeModal="reDetail()" :review_pk="board_pk" :room_pk="room_pk"> </detail-modal>
        <div class="font-neodgm" v-if="!showCreate && !showDetail">
            <div class="overlay" @click="$emit('closeModal')" id="modal-1"></div>
            <div class="modal-card">
              <span1 class="fs-1" style="margin:0 0 0 30%">게시판</span1>
              <el-button @click="$emit('closeModal')" style="border:none; margin:0 0 0 30%" ><i class="el-icon-close"></i></el-button>
              <div class="row">
              <div class="col-1"></div>
              <div class="col-10">
              <el-button id="right" @click="movetoCreate" style="border:none" circle>글 작성<i class="el-icon-edit"></i></el-button>
              <hr>
              <table class="table " >
              <colgroup>
              <col width="20%"  />
              <col width="35%" />
              <col width="20%" />
              <col width="25%" />
              </colgroup>
              <thead class="table-primary" style="background-color:#8EBF24; color:white;">
                  <tr>
                  <th scope="col" style="background-color:#8EBF24;">#</th>
                  <th scope="col" style="background-color:#8EBF24;">제목</th>
                  <th scope="col" style="background-color:#8EBF24;">글쓴이</th>
                  <th scope="col" style="background-color:#8EBF24;">조회수</th>
                  </tr>
              </thead>
              <tbody id="userlist"
              v-for="(article, i) in itemsForList" :key="i">
                  <tr class="bb" :class="{black:article.mouseOn}">
                  <th scope="row">{{ i+1 }}</th>
                  <td class="left overflow-hidden title " 
                  @click="reviewDetail(article.id)"
                  @mouseover="article.mouseOn=true" 
                  @mouseout="article.mouseOn=false" 
                  >
                  {{ article.title }}
                  </td>
                  <td>{{ article.user.userid }}</td>
                  <td>{{ article.views }}</td>
                  </tr>
              </tbody>
              </table>
              <el-pagination
              @current-change="userlist"
              :current-page.sync="currentPage"
              :page-size="perPage"
              layout="prev, pager,next"
              :total="rows"
              ></el-pagination>
              </div>
              <div class="col-1"></div>
              </div>
        </div>
    </div>
    </div>
</template>
<script>
import axios from '../../js/axios'
import createModal from './BoardCreate.vue'
import detailModal from './BoardDetailmodal.vue'
export default {
    components:{
        createModal,
        detailModal,
    },
    props:[
        'temp'
    ],
data: function () {
    return {
      
      articles: [],
      board_pk: '',
      perPage: 5,
      currentPage: 1,
      room_pk:'',
      showCreate:false,
      showDetail:false,
    }
  },
  computed: {
    rows() {
      return this.articles.length;
    },
    itemsForList() {
      return this.articles.slice(
        (this.currentPage - 1) * this.perPage,
        this.currentPage * this.perPage)
    },
  },
  methods: {
    getroompk(){
        this.room_pk = document.getElementById('roomPk').value
    },
    movetoCreate(){
        this.showCreate = true
    },
    movetoDetail(){
      this.showDetail = true
    },
    reviewDetail: function (no) {
      this.showDetail=true
      this.board_pk = no
    },
    restart(){
      axios({
        url:`/room/${this.room_pk}/board/`,
        method:'GET',
      })
      .then(res=>{
        this.articles = res.data
      })
      this.showCreate=false
    },
    reDetail(){
      axios({
        url:`/room/${this.room_pk}/board/`,
        method:'GET',
      })
      .then(res=>{
        this.articles = res.data
      })
      this.showDetail = false
    },
    selectArticle() {
      this.mouseOn = !this.mouseOn
    }
  },

  created(){
    this.getroompk()
    axios({
        url:`/room/${this.room_pk}/board/`,
        method:'GET',
      })
      .then(res=>{
        console.log(res)
        this.articles = res.data
      })
  }
 
}
</script>
<style>
.modal

.overlay {
    width: 100%; 
    height: 100%; 
    position: fixed;
     left: 0; top: 0; 
}
.overlay { 
    opacity: 0.5; 
    background-color: black; 
}
.modal-card { 
    position: relative; 
    max-width: 50%;
    margin: auto; 
    margin-top: 30px; 
    padding: 20px; 
    background-color: white; 
    min-height: 500px; 
    z-index: 10; 
    opacity: 1; 
}
#right {
    margin-left: 80%;
    color:black;
}
</style>