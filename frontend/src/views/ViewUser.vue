<template>
    <div class="ViewUser">
        <h1>User Management</h1>
        <div class="btn-group" role="currentUser" >
            <button type="button" class="btn btn-secondary" @click="home">Home</button>
            <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
        </div>
        <br><br>
        <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">

            <label for="role">Filter by roles:</label>
            <br>
            <select name="selectRole" id="selectRole">
                <option v-for="item in dropdownData" :key="item" v-bind:value="item">{{item}}</option>
            </select>

            <table class="table" id="showUserTable">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Username</th>
                        <th scope="col">Email</th>
                        <th scope="col">User Type</th>
                        <th scope="col" colspan="2">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item, i in data" :key="item.userId">
                        <td>{{ i+1 }}</td>
                        <td>{{ item.username }}</td>
                        <td>{{ item.email }}</td>
                        <td>{{ item.userType }}</td>
                        <td><button class="btn btn-warning">Edit</button></td>
                        <td><button class="btn btn-danger">Delete</button></td>
                    </tr>
                </tbody>
            </table>                 
        </div>
    </div>
    </template>
    
    <script>
    import axios from "axios";

    /*
        To user jquery for page length, example and sample in the following link
        https://www.freakyjolly.com/how-to-use-jquery-datatables-in-vue-js-tutorial-by-example/
    */

    export default {
        name: 'ViewUser',
        data () {
            return {
                data: [],
                userType: localStorage.userType,
                dropdownData: ['ALL', 'ROLE_ADMIN','ROLE_APPROVER','ROLE_VENDOR'],
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
        },
        created()
        {
            try
            {
                console.log("viewing user");
                axios.get("http://localhost:8080/api/users/all", {
                        headers:{
                            "Content-Type": "application/json",
                            "Authorization": "Bearer " + localStorage.token,
                            "Access-Control-Allow-Origin": "*",
                        }
                    })
                    .then((response) => {
                        console.log(response.data);
                        this.data = response.data;
                    })                                   
            }
            catch(e)
            {
            if(e instanceof SyntaxError)
            {
                this.$router.push({name: 'Login'});
            }
            else
            {
                console.log(e);
            }
            }
        },
    }
    </script>
    