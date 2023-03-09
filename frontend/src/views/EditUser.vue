<template>
    <div class="edituser">
        <h1>Update {{ editUsername }} account</h1>
        <div class="btn-group" role="currentUser" >
            <button type="button" class="btn btn-secondary" @click="home">Home</button>
            <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
        </div>
        <br><br>
        <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
            <table class="table">
                <tbody>
                    <tr v-for="(v, k) in data" :key="k.userid">
                        <td v-if="k !== 'token' && k !== 'tokenType' && k !== 'userId' && k !== 'password'"><label>{{ k }}</label></td>
                        <td v-if="k !== 'token' && k !== 'tokenType' && k !== 'userId' && k !== 'password'"><input type=text v-bind:id="k" v-bind:value="v" style="width: 100%"></td>                    
                    </tr>
                    
                    <tr>
                        <td><label>[Optional]<br>Change Password</label></td>
                        <td>
                            <input type="password" id="changePassword" v-model="changePassword" style="width: 100%" placeholder="Only enter password here if you want to change password">
                        </td>
                    </tr> 
                    <tr>
                        <td><label>Password</label></td>
                        <td>
                            <input type="password" id="password" v-model="password" style="width: 100%" placeholder="Enter your own password to confirm changes">
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
    export default {
        name: 'UpdateAccount',
        data () {
            return {
                editUsername: JSON.parse(localStorage.editUser).username,
                data: JSON.parse(localStorage.editUser),
                userType: localStorage.userType,
                email: "",
                password: "",
                changePassword: "",
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
                if(this.userType === 'ROLE_ADMIN' || this.userType === 'ROLE_APPROVER')
                {
                    // update the data
                    this.updateAccount();
                }
            },
            reset: function()
            {
                // reset the data
                this.data = JSON.parse(localStorage.editUser);
            },
            updateAccount()
            {
                if(this.password === "")
                {
                    alert("Please enter your current password to confirm changes");
                    return;
                }
    
                axios.post("http://localhost:8080/api/auth/login", {
                    email: JSON.parse(localStorage.data).email,
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
                            console.log(document.getElementById(v).value);
    
                            data += '"' + v + '":"' + document.getElementById(v).value + '",';
                            
                            console.log("====")
    
                        }
    
                        if(this.changePassword !== "")
                        {
                            this.password = this.changePassword;
                            data += '"password":"' + this.password + '"';
                        }
                        else
                        {
                            data = data.substring(0, data.length - 1);
                        }
    
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
                            localStorage.userType = response_users.data.userType;
                            localStorage.data = JSON.stringify(response_users.data);
    
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
        },
        created()
        {
            if(this.userType === undefined)
            {
                this.$router.push({name: 'Login'});
            }
        },
    }
    </script>
    