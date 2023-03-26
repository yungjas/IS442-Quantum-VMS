<template>
    <div class="d-flex justify-content-between" v-if="loggedIn">
        <a href="/" class="d-flex">
            <img src="../assets/quantum-leap-incorporation2.jpg" class="p-2 m-2" id="navLogo">
        </a>

        <div class="d-flex flex-row-reverse p-3 m-2">
            <div class="d-flex flex-column p-2">
              <div>{{user.username}}</div>
              <a href="javascript:void(0)" @click="logout">Logout</a>
            </div>
            <a href="/updateaccount" v-if="user.userType === 'ROLE_ADMIN'">
                <img src="../assets/user.png" width="40" height="40">  
            </a>
            <a href="/viewaccount" v-if="user.userType === 'ROLE_VENDOR'">
                <img src="../assets/user.png" width="40" height="40">  
            </a>
        </div>
    </div>
</template>

<script>
//import axios from "axios";

export default {
    name: "Navbar",
    data(){
        return{
            user: Object,
            loggedIn: false
        }
    },
    watch: {
        $route(){
            if(localStorage.token){
                this.loggedIn = true
                this.user = JSON.parse(localStorage.user)
            }
            else{
                this.loggedIn = false
            }   
        }
    },
    methods: {
        logout: function() {
            localStorage.clear()
            this.$router.push("/login")
        }
    }
}

</script>

<style>
    #navLogo{
        width: 43%;
    }

    /* #userLogo{
        width: 10%;
    } */

</style>