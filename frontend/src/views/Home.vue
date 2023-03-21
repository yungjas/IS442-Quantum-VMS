<template>
  <!-- NOTE, this is temporary, nav bar should be coded into App.vue as a template across site -->

  <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#"> Welcome back, {{ username }}</a>

  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">

      <li class="nav-item active">
        <a class="nav-link" href="../">Home </a>
      </li>

      <li class="nav-item">
        <a class="nav-link" href="/about">About</a>
      </li>

      <li class="nav-item">
        <a class="nav-link" href="/updateAccount">Update Account</a>
      </li>

      <li class="nav-item">
        <a class="nav-link" href="/sendEmail">Send Email</a>
      </li>

    </ul>

      <span class="nav-item">
        <a class="nav-link" href="/login">Logout</a>
      </span>

      
    

      </div>
    </nav>

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
  </div>
  <div v-else-if="userType === 'ROLE_VENDOR'">
    <div class="btn-group" role="currentUser" >
      <button type="button" class="btn btn-secondary" @click="home">Home</button>
      <button type="button" class="btn btn-secondary" @click="about">About</button>
      <button type="button" class="btn btn-secondary" @click="viewAccount">View My Account</button>
      <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
    </div>
    <br><br>
    Workflow (Not coded yet)
    <br>
    <div class="btn-group" role="workflowGroup" >
      <button type="button" class="btn btn-secondary">View</button>
    </div>
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
