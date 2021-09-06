import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import axios from '../js/axios'
export default new Vuex.Store({
  state: {
    isLogin:false, //현재 유저
    userid:'',
    isUnity:false,
    unityInstance:undefined,
    username:'',
  },
  mutations: {
    loginSuccess(state,payload){
      console.log(payload,"hello")
      state.isLogin = true
      state.userid = payload.userid
      state.username = payload.username
    },
    logout(state){
      state.isLogin = false
      state.userid = ''
      localStorage.removeItem('accessToken')
    },
    getUnityIn(state,payload){
      state.unityInstance = payload
    }
  },
  actions: {
    requestWrite({state},payload){
      return axios({
        url:'/room/board/',
        method:'post',
        data:payload
      })
    },
    // return 형식으로 작성해줘야 component에서 promise처리 가능
    requestLogin({state},payload){
      return axios({
        url:'/users/login',
        method:'post',
        data:payload
      })
    },
    requestSignup({state},payload){
      return axios({
        url:'/users',
        method:'post',
        data:payload
      })
    },
    requestIDCheck({state},payload){
      return axios({
        url:'/users/id/'+payload.id+'/',
        method:'get',
      })
    },
    getUserInfo({state},payload){
      axios
        .get("/users/me/", {
          headers: {
          Authorization: `Bearer ${payload}`
          }
        })
        .then(res=>{
          let userid = res.data.userId
          console.log(res,'res')
          let username = res.data.username
          let data ={'userid':userid, 'username':username}
          this.commit('loginSuccess',data)
        })
        .catch(err=>{
          console.log(err)
          localStorage.removeItem('accessToken')
          // this.$router.push({name:'Home'})
        })
    },
    //profile페이지에서 user정보 받아오기
    getThisUser({state},payload){
      return axios({
        url:'/users/'+payload.userid+'/',
        method:'get',
        data:payload,
      })
    },
    requestUpdate({state},payload){
      return axios({
          url:'/users/'+payload.userid+'/',
          method:'put',
          data:payload.data,
          headers: {
          Authorization: `Bearer ${localStorage.getItem('accessToken')}`
          },
        })
    },
    deleteUser({state},payload){
      const token = localStorage.getItem('accessToken')
      return axios({
        url:'/users/'+payload.userid+'/',
        method:'delete',
        data:payload,
        headers: {
          Authorization: `Bearer ${token}`
        },
      })
    },
    IdCheck({state},payload){
      return axios({
        url:'/users/id/'+payload.userId+'/',
        method:'get',
        data:payload,
      })
    }
  },
  modules: {},
});
