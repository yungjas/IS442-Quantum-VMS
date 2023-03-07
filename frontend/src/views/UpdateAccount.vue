<template>
<div class="updateAccount">
    <h1>Update my account</h1>
    <div class="btn-group" role="currentUser" >
        <button type="button" class="btn btn-secondary" @click="home">Home</button>
        <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
    </div>
    <br><br>
    <!-- if role = ROLE_ADMIN for loop data -->
    <!-- create a 2 by 2 table with bootstrap-->


    <div v-if="userType === 'ROLE_ADMIN'">
        <table class="table">
            <tbody>
                <tr v-for="(v, k) in data" :key="k.userid">
                    <td v-if="k !== 'token' && k !== 'tokenType' && k !== 'userId'"><label>{{ k }}</label></td>
                    <td v-if="k !== 'token' && k !== 'tokenType' && k !== 'userId'"><input type=text v-bind:id="k" v-bind:value="v" style="width: 100%"></td>                    
                </tr>
                <tr>
                    <td><label>Password</label></td>
                    <td>
                        <input type="password" id="password" v-model="password" style="width: 100%" placeholder="Enter current password to confirm changes">
                    </td>
                </tr>         
            </tbody>
        </table>

        <div class="btn-group" role="submitChange">
            <button type="button" class="btn btn-secondary" @click="update">Update</button>
            <button type="button" class="btn btn-secondary" @click="reset">Reset</button>
        </div>
    </div>
</div>
</template>

<script>
import axios from "axios";
    // @ is an alias to /src
    //import HelloWorld from '@/components/HelloWorld.vue'

    export default 
    {
    name: 'updateaccount',
    data () {
        return {
            data: JSON.parse(localStorage.data),
            userType: localStorage.userType,
            password: "",
        }
    },
    methods: 
    {
        home: function()
        {
        this.$router.push({name: 'Home'});
        },
        logout: function()
        {
            localStorage.clear();
            this.$router.push({name: 'Login'});
        },
        update: function()
        {
            // update the data
            if(this.userType === 'ROLE_ADMIN')
            {
                // update the data
                this.updateAdmin();
            }
        },
        reset: function()
        {
            // reset the data
            this.data = JSON.parse(localStorage.data);
        },
        updateAdmin()
        {
            if(this.password === "")
            {
                alert("Please enter your current password to confirm changes");
                return;
            }

            axios.post("http://localhost:8080/api/auth/login", {
                email: document.getElementById("email").value,
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
                if(response.status == 200)
                {
                    var data = '{';

                    for(var v in this.data)
                    {
                        console.log(v);
                        
                        

                        if(v === "tokenType" || v === "token" || v ==="userId")
                        {
                            console.log("NO DATA BECAUSE THIS IS NOT REQUIRED IN BODY");
                            console.log("====")
                            continue;
                        }
                        // if(v === "userType")
                        // {
                        //     //add to data as array
                        //     data += '"userType":"' + document.getElementById(v).value + '",';
                        //     continue;
                        // }

                        console.log(document.getElementById(v).value);

                        data += '"' + v + '":"' + document.getElementById(v).value + '",';
                        
                        console.log("====")

                    }
                    data += '"password":"' + this.password + '"';
                    data += '}';
                    data = JSON.parse(data);
                    console.log(data);
                    console.log("==")
                    console.log(JSON.stringify(data))

                    axios.put("http://localhost:8080/api/users/update_user/" + this.data.userId, data, {
                        headers:{
                            "Content-Type": "application/json",
                            "Authorization": "Bearer " + localStorage.token,
                            "Access-Control-Allow-Origin": "*",
                        }
                    })
                    .then((response_users) => {
                        console.log(response_users);
                        localStorage.clear();
                        localStorage.token = response.data.token;
                        localStorage.userType = response.data.userType;
                        localStorage.data = JSON.stringify(response.data);

                        alert("Account updated successfully");
                    })                        
                }
            }).catch(function (error) 
            {
                if(error.response.status == 401)
                {
                    alert("Incorrect password");
                }
                else
                {
                    alert("Error: " + error.response.status);
                }
            });
        },
        // initApprover()
        // {

        // },
        // initVendor()
        // {

        // },
    },
    created()
    {
        if(this.userType === undefined)
        {
            this.$router.push({name: 'Login'});
        }
        // else if(this.userType === 'ROLE_ADMIN')
        // {
        
        // }
        // else if(this.userType === 'ROLE_APPROVER')
        // {
        
        // }
        // else if(this.userType === 'ROLE_VENDOR')
        // {
        
        // }
        
        this.username = this.data.username;

    }
    }
</script>
