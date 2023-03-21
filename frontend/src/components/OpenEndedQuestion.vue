<template>
    <div>
      <label>{{ questionText }}</label>
      <textarea v-model="answer" />
    </div>
  </template>
  
  <script>
  export default {
    name: "OpenEndedQuestion",
    props: {
      questionText: {
        type: String,
        required: true,
      },
    },
    data() {
      return {
        answer: "",
      };
    },
  };
  </script>
  
  <style scoped>
  label {
    font-weight: bold;
  }
  textarea {
    width: 100%;
  }
  </style>
  