<template>
    <div class="createUser" style="margin-top: 2em;">
        <h1>Create an account</h1>
        <!-- <div class="btn-group" role="currentUser" >
            <button type="button" class="btn btn-secondary" @click="home">Home</button>
            <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
        </div> -->
        <br><br>
        <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
            <table class="table table-borderless">
                <tbody>
                    <tr>
                        <td>USERTYPE</td>
                        <td>
                            <select style="width: 80%" name="selectRole" id="selectRole" @change="onChange($event)">
                                <option v-for="item in dropdownData" :key="item" v-bind:value="item">{{item}}</option>
                            </select>                            
                        </td>    
                    </tr>
                    <tr v-for="(v, k) in data" :key="k.userid">
                        <td v-if="v !== 'userId' && v !== 'userType'"><label>{{ v.toUpperCase() }}</label></td>
                        <td v-if="v !== 'userId' && v !== 'userType' && v !== 'password'">
                            <input type=text v-bind:id="v" style="width: 80%">
                        </td>
                        <td v-else-if="v !== 'userId' && v !== 'userType'">
                            <input type=password v-bind:id="v" style="width: 80%">
                        </td>
                    </tr>
                </tbody>
            </table>
    
            <div class="btn-group" role="submitChange">
                <button type="button" class="btn btn-secondary" @click="createUser">Create</button>
            </div>
        </div>
    </div>
</template>
    
<script>
    import axios from "axios";

    export default {
        name: 'CreateUser',
        data () {
            return {
                dropdownData: ['ROLE_ADMIN','ROLE_APPROVER','ROLE_VENDOR'],
                role: '',
                data: [],                
                userType: localStorage.userType,
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
                if(this.userType === 'ROLE_ADMIN' || this.userType === 'ROLE_APPROVER')
                {
                    if(this.role !== 'ROLE_VENDOR')
                    {
                        //Create user
                        var createUser = "{";

                        //usertype
                        createUser += '"userType":"' + this.role + '",';

                        //Create Vendor
                        for(var x in this.data)
                        {
                            if(this.data[x] == 'userId' || this.data[x] == 'userType')
                            {
                                continue;
                            }
                            var tempData2 = this.data[x];
                            console.log(tempData2);
                            createUser += '"' + tempData2 + '":"' + document.getElementById(tempData2).value + '",';
                            
                            console.log("====")                            
                        }
                        createUser = createUser.slice(0, -1);
                        createUser += "}";

                        console.log(createUser);

                        createUser = JSON.parse(createUser);

                        console.log(createUser);
                        axios.post("http://localhost:8080/api/users/create_user", createUser, {
                                headers:{
                                    "Content-Type": "application/json",
                                    "Authorization": "Bearer " + localStorage.token,
                                    "Access-Control-Allow-Origin": "*",
                                }
                            })
                            .then((response_users) => {
                                console.log(response_users);
                                this.$router.push({name: 'Home'}); 
                                alert("Account created successfully");               
                            })                          
                    }
                    else
                    {
                        var createData = "{";

                        //Create Vendor
                        for(var v in this.data)
                        {
                            if(this.data[v] == 'userId' || this.data[v] == 'userType')
                            {
                                continue;
                            }
                            var tempData = this.data[v];
                            console.log(tempData);
                            createData += '"' + tempData + '":"' + document.getElementById(tempData).value + '",';
                            
                            console.log("====")                            
                        }
                        createData = createData.slice(0, -1);
                        createData += "}";

                        console.log(createData);

                        createData = JSON.parse(createData);

                        console.log(createData);
                        axios.post("http://localhost:8080/api/users/create_vendor", createData, {
                                headers:{
                                    "Content-Type": "application/json",
                                    "Authorization": "Bearer " + localStorage.token,
                                    "Access-Control-Allow-Origin": "*",
                                }
                            })
                            .then((response_users) => {
                                console.log(response_users);

                                this.$router.push({name: 'Home'});
                                        
                                alert("Account created successfully");
                            })                               
                    }

                    
                }
            },
            onChange(event) 
            {
                console.log(event.target.value);
                this.role = event.target.value;

                if(this.role == 'ROLE_ADMIN' || this.role == 'ROLE_APPROVER')
                {
                    this.getUserAttribute();
                }
                else
                {
                    this.getVendorAttribute();
                }
            },
            getUserAttribute()
            {
                axios.get("http://localhost:8080/api/roles/user", 
                {
                    headers:{
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + localStorage.token,
                        "Access-Control-Allow-Origin": "*",
                    }
                    })
                    .then((response) => {

                        this.data = [];
                        this.data = response.data;

                        // for(var i = 0; i < this.data.length; i++)
                        // {
                        //     console.log(this.data[i]);
                        // }
                })                                    
            },
            getVendorAttribute()
            {
                axios.get("http://localhost:8080/api/roles/vendor", 
                {
                    headers:{
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + localStorage.token,
                        "Access-Control-Allow-Origin": "*",
                    }
                    })
                    .then((response) => {

                        this.data = [];
                        this.data = response.data;
                        console.log(this.data);
                })                                    
            }
        },
        created()
        {
            this.getUserAttribute();
            this.role = 'ROLE_ADMIN';
        },
    }
    </script>
    