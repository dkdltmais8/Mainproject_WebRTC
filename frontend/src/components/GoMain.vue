<template>
    <div class="container font-neodgm">
        <!-- <b-modal class="font-neodgm"> -->
        <div class="overlay" @click="$emit('closemodal')" id="modal-1"></div>
        <div class="modal-card">
            <img src="@/assets/Facetime.png" alt="facetime" style="width:200px;" class="me-3">
            <h1 class="mt-2">화상연결 하시겠습니까?</h1>
            <el-button @click="$emit('movetomain')" style="background-color:#8EBF24; color:white;" class="fs-4">시작</el-button> 
            <el-button @click="$emit('closemodal')" type="warning" class="fs-4">아니요</el-button>
        </div>
    </div>
</template>
<script>
export default {
    created(){
        console.log("gomain뜸")
    },
}
</script>
<style>
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
    z-index: 100; 
    opacity: 1; 
}
</style>