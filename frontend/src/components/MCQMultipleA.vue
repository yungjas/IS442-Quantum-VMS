<template>
    <div class="question">
      <div class="question-text">{{ questionText }}</div>
      <div class="answer-choices">
        <div
          class="answer-choice"
          v-for="(choice, index) in answerChoices"
          :key="index"
        >
          <input
            type="checkbox"
            :id="'choice-' + index"
            :name="'choice-' + index"
            :value="choice"
            v-model="selectedAnswers"
          />
          <label :for="'choice-' + index">{{ choice }}</label>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      questionText: String,
      answerChoices: Array,
    },
    data() {
      return {
        selectedAnswers: [],
      };
    },
  };
  </script>
  
  <style scoped>
  .question {
    margin-bottom: 20px;
  }
  
  .question-text {
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .answer-choices {
    margin-left: 20px;
  }
  
  .answer-choice {
    margin-bottom: 5px;
  }
  </style>
  