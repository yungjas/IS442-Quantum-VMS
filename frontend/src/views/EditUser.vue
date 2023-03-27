<template>
    <div class="createUser">
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
                        <td v-if="k !== 'token' && k !== 'tokenType' && k !== 'userId' && k !== 'password' && k !== 'userType'"><input type=text v-bind:id="k" v-bind:value="v" style="width: 80%"></td>                    
                        <td v-else-if="k === 'userType'" style="padding-left: 5em;">
                            <select class="form-control" v-model="selected" :required="true" @change="changeLocation" style="width: 89%;">
                                <option :selected="true" id="selectedUserType">{{editUserType}}</option>
                                <option v-for="userType in userTypes" id="selectedUserType" v-bind:key="userType" v-bind:value="userType">{{ userType }}</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label>PASSWORD</label></td>
                        <td>
                            <input type="password" id="password" style="width: 80%" placeholder="Enter current password to confirm changes">
                        </td>
                    </tr>
                    <tr>
                        <td><label>[Optional]<br>CHANGE PASSWORD</label></td>
                        <td>
                            <input type="password" id="changePassword" style="width: 80%" placeholder="Only enter password here if you want to change password">
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

                userTypes: ["ROLE_ADMIN", "ROLE_APPROVER", "ROLE_VENDOR"],
                data: JSON.parse(localStorage.editUser),
                userType: localStorage.userType,
                editUserType: "",
                email: "",
                password: "",
                changePassword: "",
                selected: "",
                

            }
        },
        methods: 
        {
            home: function()
            {
                localStorage.removeItem("editUser")
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

            changeLocation()
            {
                // var a  = document.getElementById("selectedUserType").value;
                console.log(this.selected);
            },
            updateAccount()
            {
                this.password = document.getElementById("password").value;
                this.changePassword = document.getElementById("changePassword").value;

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
                            

                            if(v === "tokenType" || v === "token" || v ==="userId" || v === "password" || v === "userType")

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

                        
                        data += '"userType":"' + this.selected + '",';

    
                        if(this.changePassword !== "")
                        {
                            data += '"password":"' + this.changePassword + '"';
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
                        console.log(this.data.userId);

                        if(this.changePassword !== "")
                        {
                            axios.put("http://localhost:8080/api/users/update_user/" + this.data.userId, data, {
                                headers:{
                                    "Content-Type": "application/json",
                                    "Authorization": "Bearer " + localStorage.token,
                                    "Access-Control-Allow-Origin": "*",
                                }
                            })
                            .then((response_users) => {
                                console.log(response_users);
                                localStorage.removeItem("editUser")
                                this.$router.push({name: 'ViewUser'})
        
                                alert("Account updated successfully with new password");
                            })                            
                        }
                        else
                        {
                            axios.put("http://localhost:8080/api/users/update_other_user/" + this.data.userId, data, {
                                headers:{
                                    "Content-Type": "application/json",
                                    "Authorization": "Bearer " + localStorage.token,
                                    "Access-Control-Allow-Origin": "*",
                                }
                            })
                            .then((response_users) => {
                                console.log(response_users);
                                localStorage.removeItem("editUser")
                                this.$router.push({name: 'ViewUser'})
        
                                alert("Account updated successfully");
                            })                                
                        }                        
    

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
            console.log(this.data.userType);
            this.editUserType = this.data.userType;

            this.selected = this.editUserType;
            //for i in userTypes
            for(var i = 0; i < this.userTypes.length; i++)
            {
                if(this.userTypes[i] === this.editUserType)
                {
                    this.userTypes.splice(i, 1);
                    break;
                }
            }
        },
    }
</script>

    