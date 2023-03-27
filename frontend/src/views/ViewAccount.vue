<template>
    <div class="updateAccount">

        <h1>View my account</h1>
        <div class="btn-group" role="currentUser" >
            <!-- <button type="button" class="btn btn-secondary" @click="home">Home</button>
            <button type="button" class="btn btn-secondary" @click="logout">Logout</button> -->

        </div>
        <br><br>

        <div v-if="userType === 'ROLE_VENDOR'">
            <table class="table">
                <tbody>
                    <tr v-for="(v, k) in data" :key="k.userid">

                        <td v-if="k !== 'token' && k !== 'tokenType' && k !== 'userId' && k !== 'password'"><label>{{ k.toUpperCase() }}</label></td>

                        <td v-if="k !== 'token' && k !== 'tokenType' && k !== 'userId' && k !== 'password'"><input type=text v-bind:id="k" v-bind:value="v" style="width: 100%" readonly></td>                    
                    </tr>                       
                </tbody>
            </table>
        </div>    
    </div>

</template>
    
<script>    
    export default {

        name: 'viewaccount',
        data () {
            return {
                data: JSON.parse(localStorage.data),
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
        },
        created()
        {
            if(this.userType === undefined)
            {
                this.$router.push({name: 'Login'});
            }
            else if(this.userType !== "ROLE_VENDOR")
            {
                this.$router.push({name: 'Home'});
            }
            
            this.username = this.data.username;
        },
    }
    </script>
    