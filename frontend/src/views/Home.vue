<template>
  <!-- NOTE, this is temporary, nav bar should be coded into App.vue as a template across site -->
  <div>
    <h1>Welcome back, {{ username }}</h1>
    <!-- <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
      <div class="btn-group" role="currentUser" > -->
        <!-- <button type="button" class="btn btn-secondary" @click="home">Home</button>
        <button type="button" class="btn btn-secondary" @click="about">About</button> -->
        <!-- <button type="button" class="btn btn-secondary" @click="updateAccount">Update My Account</button>
        <button type="button" class="btn btn-secondary" @click="sendEmail">Send Email</button>
        <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
      </div>
      <br><br>
      User Management
      <br>
      <div class="btn-group" role="userGroup" >
        <button type="button" class="btn btn-secondary" @click="viewUser">View/Update/Delete</button>
        <button type="button" class="btn btn-secondary" @click="createUser">Create</button>
      </div>
      <br><br>
      Workflow Management (Not coded yet)
      <br>
      <div class="btn-group" role="workflowGroup">
        <button type="button" class="btn btn-secondary">View/Update/Delete</button>
        <button type="button" class="btn btn-secondary">Create</button>
      </div>
      <br><br>
      Form Management (Not coded yet)
      <br>
      <div class="btn-group" role="formGroup">
        <button type="button" class="btn btn-secondary">View/Update/Delete</button>
        <button type="button" class="btn btn-secondary" @click="createForm">Create</button>
      </div>
     </div> -->


</div>

</template>
<script>
// @ is an alias to /src
//import HelloWorld from '@/components/HelloWorld.vue'

export default 
{
  name: 'home',
  data () {
    return {      
      data: null,
      userType: null,
      username: "",
    }
  },
  methods: 
  {
    home: function()
    {
      this.$router.push({name: 'Home'});
    },
    about: function()
    {
      this.$router.push({name: 'About'});
    },
    updateAccount: function()
    {
      this.$router.push({name: 'UpdateAccount'});
    },
    viewAccount: function()
    {
      this.$router.push({name: 'ViewAccount'});
    },
    logout: function()
    {
      localStorage.clear();
      this.$router.push({name: 'Login'});
    },
    viewUser: function()
    {
      this.$router.push({name: 'ViewUser'});
    },
    createUser: function()
    {
      this.$router.push({name: 'CreateUser'});
    },
    sendEmail: function()
    {
      this.$router.push({name: 'SendEmail'});
    },
    createForm: function()
    {
      this.$router.push({name: 'CreateForm'});
    }
    // initAdmin()
    // {

    // },
    // initApprover()
    // {

    // },
    // initVendor()
    // {

    // },
  },
  created()
  {
    try
    {
      console.log("running");
      this.data = JSON.parse(localStorage.data);
      this.userType = localStorage.userType;
      this.username = this.data.username;
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
    // if(this.userType === null)
    // {
    //   this.$router.push({name: 'Login'});
    // }
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
