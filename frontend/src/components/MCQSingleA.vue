<template>
    <div>
      <label>{{ question }}</label>
      <div v-for="(choice, index) in choices" :key="index">
        <input type="radio" :value="choice" v-model="answer">
        <span>{{ choice }}</span>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      question: String,
      choices: Array
    },
    data() {
      return {
        answer: ''
      }
    }
  }
  </script>
  