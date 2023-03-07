<template>
    <div>
        <form @submit.prevent="login()">
            <div>
                <labeL>Email</labeL>
                <input type="text" v-model="email"/>
            </div>
            <div>
                <labeL>Password</labeL>
                <input type="password" v-model="password"/>
                <br>
                {{ email }} 
                <br>
                {{  password }}
            </div>
            <button>Login</button>
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
                    
                    // this.$router.push('/');

                    //Replace the following with router.push
                    localStorage.userType = response.data.userType;
                    localStorage.data = JSON.stringify(response.data);

                    this.$router.push({ name: 'Home'})      

                    // testing if localStorage works
                    // axios.get("http://localhost:8080/api/users/all", {
                    //     headers:{
                    //         "Content-Type": "application/json",
                    //         "Authorization": "Bearer " + localStorage.token,
                    //         "Access-Control-Allow-Origin": "*",
                    //     }
                    // })
                    // .then((response_users) => {
                    //     console.log(response_users);
                    // })

                }
            });
        },

        logout: function(){
            localStorage.clear();

            // testing if logout works
            // axios.get("http://localhost:8080/api/users/all", {
            //     headers:{
            //         "Content-Type": "application/json",
            //         "Authorization": "Bearer " + localStorage.token,
            //         "Access-Control-Allow-Origin": "*",
            //     }
            //     })
            //     .then((response_users) => {
            //         console.log(response_users);
            //     })
        }
    }
}
</script>
