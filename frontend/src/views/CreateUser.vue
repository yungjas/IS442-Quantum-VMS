<template>
    <div class="createUser">
        <h1>Create an account</h1>
        <div class="btn-group" role="currentUser" >
            <button type="button" class="btn btn-secondary" @click="home">Home</button>
            <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
        </div>
        <br><br>
        <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
            <!-- <table class="table">
                <tbody>
                    <tr v-for="(v, k) in data" :key="k.userid">
                        <td v-if="k !== 'token' && k !== 'tokenType' && k !== 'userId' && k !== 'password'"><label>{{ k }}</label></td>
                        <td v-if="k !== 'token' && k !== 'tokenType' && k !== 'userId' && k !== 'password'"><input type=text v-bind:id="k" v-bind:value="v" style="width: 100%"></td>                    
                    </tr>
                    <tr>
                        <td><label>Password</label></td>
                        <td>
                            <input type="password" id="password" v-model="password" style="width: 100%" placeholder="Enter current password to confirm changes">
                        </td>
                    </tr>
                    <tr>
                        <td><label>[Optional]<br>Change Password</label></td>
                        <td>
                            <input type="password" id="changePassword" v-model="changePassword" style="width: 100%" placeholder="Only enter password here if you want to change password">
                        </td>
                    </tr>                        
                </tbody>
            </table>
    
            <div class="btn-group" role="submitChange">
                <button type="button" class="btn btn-secondary" @click="update">Update</button>
                <button type="button" class="btn btn-secondary" @click="reset">Reset</button>
            </div> -->
        </div>
    </div>
    </template>
    
    <script>
    // import axios from "axios";

    export default {
        name: 'CreateUser',
        data () {
            return {
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
            createUser: function()
            {
                // update the data
                if(this.userType === 'ROLE_ADMIN' || this.userType === 'ROLE_APPROVER')
                {
                    // update the data
                    console.log("Hello");
                }
            },
        },
        created()
        {

        },
    }
    </script>
    