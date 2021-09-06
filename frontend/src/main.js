import Vue from "vue";
import App from "./App.vue";
import router from "./js/router";
import store from "./store";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/ko';
import BootstrapVue from 'bootstrap-vue';

Vue.use(ElementUI, { locale });
Vue.use(BootstrapVue);
Vue.config.productionTip = false;

const vm = new Vue({
  router,
  store,
  beforeCreate(){
    let token = localStorage.getItem('accessToken')
    if (token){
      this.$store.dispatch('getUserInfo',token)
    }
  },
  render: (h) => h(App),
}).$mount("#app");

window.app = vm;
