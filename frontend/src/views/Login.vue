<template>
    <div>
        <form @submit.prevent="login()">
            <div>
                <labeL>Username</labeL>
                <input type="text" v-model="username"/>
            </div>
            <div>
                <labeL>Password</labeL>
                <input type="password" v-model="password"/>
            </div>
            <button>Submit</button>
        </form>
        <button @click="logout">Logout</button>
    </div>
</template>

<script>
import axios from "axios";

export default{
    name: "Login",
    data() {
        return {
            username: "",
            password: "",
        }
    },
    methods: {
        login: function(){
            axios.post("http://localhost:8080/api/auth/login", {
                username: this.username,
                password: this.password,
            },
            {
                headers:{
                    "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
            }
            )
            .then((response) => {
                console.log(response);
                if(response.status == 200){
                    localStorage.token = response.data.token;
                    // testing if localStorage works
                    axios.get("http://localhost:8080/api/users/all", {
                        headers:{
                            "Content-Type": "application/json",
                            "Authorization": "Bearer " + localStorage.token,
                            "Access-Control-Allow-Origin": "*",
                        }
                    })
                    .then((response_users) => {
                        console.log(response_users);
                    })
                }
            });
        },

        logout: function(){
            localStorage.clear();

            // testing if logout works
            axios.get("http://localhost:8080/api/users/all", {
                headers:{
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + localStorage.token,
                    "Access-Control-Allow-Origin": "*",
                }
                })
                .then((response_users) => {
                    console.log(response_users);
                })
        }
    }
}
</script>
