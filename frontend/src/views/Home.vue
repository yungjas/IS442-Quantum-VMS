<template>
  <!-- NOTE, this is temporary, nav bar should be coded into App.vue as a template across site -->

  <h1>Welcome back, {{ username }}</h1>
  <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
    
    <div class="btn-group" role="currentUser" >
      <button type="button" class="btn btn-secondary" @click="home">Home</button>
      <button type="button" class="btn btn-secondary" @click="about">About</button>
      <button type="button" class="btn btn-secondary" @click="updateAccount">Update My Account</button>
      <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
    </div>
    <br><br>
    Vendor Management
    <br>
    <div class="btn-group" role="vendorGroup" >
      <button type="button" class="btn btn-secondary">View/Update/Delete</button>
      <button type="button" class="btn btn-secondary">Create</button>
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
      <button type="button" class="btn btn-secondary">Create</button>
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
      data: JSON.parse(localStorage.data),
      userType: localStorage.userType,
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
  },
}
</script>
