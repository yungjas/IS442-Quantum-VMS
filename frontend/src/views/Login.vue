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
                },
            }
            )
            .then((response) => 
                console.log(response);
                localStorage.token = response.data.token;
                
                if(response.status == 200)
                {

                    axios.get("http://localhost:8080/api/users/" + response.data.userId,
                    {
                        headers:{
                            "Content-Type": "application/json",
                            "Access-Control-Allow-Origin": "*",
                            "Authorization": "Bearer " + response.data.token,
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
                             // suggestion 1 - get user detail and store it in localstorage
                            // so that u don't have to call login in UpdateAccount.vue again
                            // axios.get("http://localhost:8080/api/users/" + response.data.userId, 
                            // {
                            //     headers:{
                            //         "Content-Type": "application/json",
                            //         "Access-Control-Allow-Origin": "*",
                            //         "Authorization": "Bearer " + localStorage.token
                            //     },
                            // })
                            // .then((response_user) => {
                            //     console.log(response_user)
                            // })
                            localStorage.data = JSON.stringify(response.data);
                            

                            this.$router.push({ name: 'Home'})      

                            // // testing if localStorage works
                            // // axios.get("http://localhost:8080/api/users/all", {
                            // //     headers:{
                            // //         "Content-Type": "application/json",
                            // //         "Authorization": "Bearer " + localStorage.token,
                            // //         "Access-Control-Allow-Origin": "*",
                            // //     }
                            // // })
                            // // .then((response_users) => {
                            // //     console.log(response_users);
                            // // })

                        }
                    });
                    
                    // localStorage.token = response.data.token;
                    
                    // // this.$router.push('/');

                    // //Replace the following with router.push
                    // localStorage.userType = response.data.userType;
                    // localStorage.data = JSON.stringify(response.data);

                    // this.$router.push({ name: 'Home'})      

                    // // testing if localStorage works
                    // // axios.get("http://localhost:8080/api/users/all", {
                    // //     headers:{
                    // //         "Content-Type": "application/json",
                    // //         "Authorization": "Bearer " + localStorage.token,
                    // //         "Access-Control-Allow-Origin": "*",
                    // //     }
                    // // })
                    // // .then((response_users) => {
                    // //     console.log(response_users);
                    // // })

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
