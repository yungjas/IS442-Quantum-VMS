<template>
  <h1>Hello world</h1>
  
  <button @click="logout">Logout</button>
</template>

<script>
// @ is an alias to /src
//import HelloWorld from '@/components/HelloWorld.vue'

export default 
{
  name: 'home',
  components: 
  {
    //HelloWorld
    
  },
  methods: 
  {
    logout: function()
    {
      localStorage.clear();
      this.$router.push({name: 'Login'});
    }
  },
  created: function()
  {
    console.log(this.$route);
  }
}
</script>
