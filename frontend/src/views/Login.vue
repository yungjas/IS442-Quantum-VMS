<template>
    <div>
        <img src="../assets/quantum-leap-incorporation2.jpg">
        <form @submit.prevent="login()" id="loginForm">
            <label for="emailInput" class="loginLabel">Email</label>
            <div class="mb-4">
                <input type="text" v-model="email" class="loginInput"/>
            </div>
            <label class="loginLabel">Password</label>
            <div class="mb-4">
                <input type="password" v-model="password" class="loginInput"/>
                <!-- <br>
                {{ email }} 
                <br>
                {{  password }} -->
            </div>
            <button id="loginBtn" class="btn btn-primary">Login</button>
        </form>
        <!-- <button @click="logout">Logout</button> -->
    </div>
</template>

<script>
import axios from "axios";

export default{
    name: "Login",
    data() {
        return {
            email: "",
            password: "",
        }
    },
    methods: {
        login: function(){
            axios.post("http://localhost:8080/api/auth/login", {
                email: this.email,
                password: this.password,
            },
            {
                headers:{
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
            }
            )
            .then((response) => 
            {
                console.log(response);
                
                if(response.status == 200)
                {
                    localStorage.token = response.data.token;
                    axios.get("http://localhost:8080/api/users/" + response.data.userId,
                    {
                        headers:{
                            "Content-Type": "application/json",
                            "Access-Control-Allow-Origin": "*",
                            "Authorization": "Bearer " + localStorage.token,
                        },
                    }
                    )
                    .then((response) => 
                    {
                        console.log(response);
                        if(response.status == 200)
                        {
                            // //Replace the following with router.push
                            localStorage.userType = response.data.userType;
                            localStorage.data = JSON.stringify(response.data);
                            
                            this.$router.push({ name: 'Home'})      

                        }
                    })
                }
            })
            .catch((error) => {
                console.log(error);
                alert("Wrong credentials, please try again")
            });
        },
        logout: function(){
            localStorage.clear();
        }
    }
}
</script>
<style>
    #loginForm{
        margin-top: 3em;
    }

    .loginLabel{
        display: inline-block;
        width: 25%;
        text-align: left;
    }

    .loginInput{
        width: 25%;
    }
    
    #loginBtn{
        margin-top: 2em;
    }
</style>