<template>
    <div class="form-builder">
      <div v-for="(question, index) in questions" :key="index">
        <component :is="question.type" :question="question"></component>
      </div>
      <div class="question-type-selection">
        <select v-model="selectedType">
          <option value="open-ended">Open-Ended Question</option>
          <option value="mcq-single-a">Multiple Choice Question (Single Answer)</option>
          <option value="mcq-multiple-a">Multiple Choice Question (Multiple Answer)</option>
          <option value="file-upload">File Upload</option>
        </select>
        <button @click="addQuestion">Add Question</button>
      </div>
    </div>
  </template>
  
  <script>
  import OpenEndedQuestion from "./OpenEndedQuestion.vue";
  import MCQSingleA from "./MCQSingleA.vue";
  import MCQMultipleA from "./MCQMultipleA.vue";
  import FileUpload from "./FileUpload.vue";
  
  export default {
    name: "FormBuilder",
    components: {
      OpenEndedQuestion,
      MCQSingleA,
      MCQMultipleA,
      FileUpload
    },
    data() {
      return {
        questions: [],
        selectedType: "open-ended"
      };
    },
    methods: {
      addQuestion() {
        let question = {
          type: this.selectedType,
          questionText: "",
          questionType: "",
          answerChoices: []
        };
        this.questions.push(question);
      }
    }
  };
  </script>
  
  <style scoped>
  .form-builder {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
  }
  </style>
  