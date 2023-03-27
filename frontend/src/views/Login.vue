<template>
    <div>

            <form text-align="center" @submit.prevent="login()">
                
                <img style="width:300px;height:300px" src= "../assets/quantum-leap-incorporation.jpg">
                
                <div>
                    <input type="text" placeholder="Email" v-model="email"/>
                </div>

                <br>

                <div>
                    
                    <input type="password" placeholder="Password" v-model="password" style="margin-bottom: 3em;"/>
                    <!-- <br>
                    {{ email }} 
                    <br>
                    {{  password }} -->
                </div>
                <button style="width: 200px" class="btn btn-primary">Login</button>
            
                <br><br>

                <!-- <button style="width: 200px" class="btn btn-secondary" @click="logout">Logout</button> -->
                <br><br>
                
            </form>
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
                    localStorage.user = JSON.stringify(response.data);
                    axios.get("http://localhost:8080/api/users/" + response.data.userId,
                    {
                        headers:{
                            "Content-Type": "application/json",
                            "Access-Control-Allow-Origin": "*",

                            "Authorization": "Bearer " + localStorage.token
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
                            localStorage.userId = response.data.userId
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
    
    /* #loginBtn{
        margin-top: 3em;
    } */
</style>