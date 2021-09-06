<template>
  <div id="scroll_box">
    <ul v-for="mess in messageList" :key="mess.id">
        <li style="list-style:none;">{{ mess[0] }} : {{ mess[1] }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name:'Message',
  props:['messageList'],
}
</script>

<style>
  #scroll_box{
    max-height: 400px;
    overflow-y: scroll;
  }
</style>
