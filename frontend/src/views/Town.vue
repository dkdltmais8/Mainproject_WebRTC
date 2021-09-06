<template>
  <div class="font-neodgm" >
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name:'Town',
  data () {
    return {
      otherNickname: '',
    }
  },
  computed:{
    ...mapState(["userid","unityInstance"]),
  },
  methods:{
    goBaek(){},
  },
  mounted() {
    var canvas = document.querySelector("#unity-canvas");
    console.log('이건 Town')
    if (this.$route.name !== "Town") {
        canvas.style.width = "300px";
        canvas.style.height = "300px";
    }
    else {
      canvas.style.width = "1120px";
      canvas.style.height = "630px";
      canvas.style.position.top ="500px";
    }
  }
}
</script>
