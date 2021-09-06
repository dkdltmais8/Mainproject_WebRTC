import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Signup from "../views/Signup.vue";
import Town from "../views/Town.vue";
import Main from "../views/Main.vue";
import Profile from '../views/Profile.vue';
import Intro from '../views/Intro.vue';
Vue.use(VueRouter);


const requireAuth = () => (to, from, next) => {
  if (localStorage.getItem('accessToken')) {
    return next();
  }
  next('/home');
};

const notLogin = () => (to,from,next) =>{
  if (!localStorage.getItem('accessToken')) {
    return next();
  }
  next('/town');
}


const routes = [
  {
    path: "/",
    name: "Intro",
    component: Intro,
  },
  {
    path: "/home",
    name: "Home",
    component: Home,
    beforeEnter: notLogin(),
  },
  {
    path: "/register",
    name: "Signup",
    component: Signup,
    beforeEnter: notLogin(),
  },
  {
    path:"/town",
    name: "Town",
    component: Town,
    beforeEnter: requireAuth(),
  },
  {
    path:"/main",
    name: "Main",
    component: Main,
    beforeEnter: requireAuth(),
  },
  {
    path:"/profile/:userid",
    name:"Profile",
    component: Profile,
    beforeEnter: requireAuth(),
  }
];

const router = new VueRouter({
  mode: 'history',
  routes,
})

export default router;
