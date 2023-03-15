<template>
    <div>
      <label>{{ label }}</label>
      <input type="file" @change="handleFileUpload" />
    </div>
  </template>
  
  <script>
  export default {
    props: {
      label: {
        type: String,
        required: true,
      },
    },
    methods: {
      handleFileUpload(event) {
        const file = event.target.files[0];
        // I need to send this file to the backend and save it to the MongoDB database
        console.log(file);
      },
    },
  };
  </script>
  