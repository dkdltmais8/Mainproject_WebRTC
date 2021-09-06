<template>
    <div class="font-neodgm">
        <div class="overlay" @click="$emit('closeModal')" id="modal-1"></div>
        <div class="modal-card">
            <h1 class="ms-1">열정 랭킹 보드판<img src="@/assets/fire.png" class="ms-1" style="width:35px" alt=""></h1>
            <table class="table mb-4" >
                <colgroup>
                <col width="30%"  />
                <col width="30%" />
                <col width="40%" />
                </colgroup>
                <thead class="table-primary" style="background-color:#8EBF24; color:white;">
                    <tr>
                    <th scope="col" style="background-color:#8EBF24;">랭킹</th>
                    <th scope="col" style="background-color:#8EBF24;">닉네임</th>
                    <th scope="col" style="background-color:#8EBF24;">공부 시간</th>
                    </tr>
                </thead>
                <tbody id="timelist" v-for="(tlist, i) in timerlist" :key="i">
                    <th scope="row" class="fs-5">{{ i+1 }}</th>
                    <td class="fs-5">{{ tlist.username }}</td>
                    <td class="fs-5">
                    {{ tlist.hour }}시간 {{tlist.min}}분 {{tlist.sec}}초
                    </td>
                </tbody>
            </table>
            <el-button @click="$emit('closeModal')" type="warning">닫기</el-button>
        </div>
    </div>
</template>
<script>
import axios from 'axios'
export default {
    data()
    {
        return{
            roompk: '',
            timerlist:'',
        }
    },
    methods:
    {
        getroompk(){
            this.room_pk = document.getElementById('roomPk').value
        },
        getTime()
        {
            axios.get(`https://i5b105.p.ssafy.io:8080/api/room/time/${this.room_pk}`).
            then(res => {
                console.log("시간");
                 console.log(res.data) 
                 this.timerlist = res.data;
            })
        }
    },

    created()
    {
        this.getroompk();
        this.getTime();
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
</style>