<template>
<div class="updateAccount">
    <h1>Update my account</h1>

    <!-- <div class="btn-group" role="currentUser" >
        <button type="button" class="btn btn-secondary" @click="home">Home</button>
        <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
    </div> -->
    <br><br>
    <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
        <table class="table table-borderless">
            <tbody>
                <tr v-for="(v, k) in data" :key="k.userid">
                    <td v-if="k !== 'token' && k !== 'tokenType' && k !== 'userId' && k !== 'password'"><label>{{ k.toUpperCase() }}</label></td>
                    <td v-if="k !== 'token' && k !== 'tokenType' && k !== 'userId' && k !== 'password'"><input type=text v-bind:id="k" v-bind:value="v" style="width: 80%"></td>                    
                </tr>
                <tr>
                    <td><label>PASSWORD</label></td>
                    <td>
                        <input type="password" id="password" v-model="password" style="width: 80%" placeholder="Enter current password to confirm changes">
                    </td>
                </tr>
                <tr>
                    <td><label>[Optional]<br>CHANGE PASSWORD</label></td>
                    <td>
                        <input type="password" id="changePassword" v-model="changePassword" style="width: 80%" placeholder="Only enter password here if you want to change password">
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
            data: JSON.parse(localStorage.data),
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
            this.data = JSON.parse(localStorage.data);
        },
        updateAccount()
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

                    if(this.changePassword !== "")
                    {
                        this.password = this.changePassword;
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
    },
}
</script>


<style>
/* .noborder{
    border-bottom: none;
} */
</style>

