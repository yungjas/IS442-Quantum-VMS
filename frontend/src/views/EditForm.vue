<template>
  <div style="margin-top: 2em">
    <h1>Edit a Form</h1>
    <!-- <div class="btn-group" role="currentUser" >
        <button type="button" class="btn btn-secondary" @click="home">Home</button>
        <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
    </div> -->
    <br /><br />

    <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
      <table class="table table-borderless">
        <tbody>
          <tr>
            <td>
              <label>Form No</label>
            </td>
            <td>
              <input type="text" v-model="data.formNo" style="width: 100%" />
            </td>
          </tr>
          <tr>
            <td>
              <label>Form Name</label>
            </td>
            <td>
              <input type="text" v-model="data.formName" style="width: 100%" />
            </td>
          </tr>
          <tr>
            <td>
              <label>Revision Number</label>
            </td>
            <td>
              <input
                type="text"
                v-model="data.revisionNo"
                style="width: 100%"
              />
            </td>
          </tr>
          <tr>
            <td>
              <label>Is Template</label>
            </td>
            <td style="text-align: left">
              <input type="checkbox" disabled v-model="data.template" />
            </td>
          </tr>
          <tr>
            <td>
              <label>Last Edited</label>
            </td>
            <td>
              <input type="date" v-model="lastEdited" style="width: 100%" />
            </td>
          </tr>
          <!-- <tr>
                        <td>
                            <label>Date Submitted</label>
                        </td>
                        <td>
                            <input type=date v-model="dateSubmitted" style="width: 100%">
                        </td>
                    </tr> -->
          <!-- <div v-if="data.template">
            hello
          </div>
          <div v-else>
            
          </div> -->
          <tr>
            <td>
              <label>Template Questions</label>
            </td>
            <td style="text-align: left">
              <div
                v-for="question in allQuestions"
                :key="question.questionId"
                class="card"
                style="margin-top: 20px"
              >
                <div class="cardbody">
                  <input
                    type="checkbox"
                    v-model="selectedQuestion"
                    :value="question"
                  />
                  &nbsp; <b>Question Text:</b>
                  <label>{{ question.questionText }}</label
                  ><br />
                  &emsp;&nbsp; <b>Question Type:</b>
                  <label>{{ question.questionType }}</label
                  ><br />
                  &emsp;&nbsp; <b>Question Section Name:</b>
                  <label>{{ question.questionSectionName }}</label
                  ><br />
                  &emsp;&nbsp; <b>Is template:</b>
                  <label>{{ question.template }}</label>
                  <br />
                  &emsp;&nbsp; <b>Answer Choices:</b> <br /><label
                    v-for="choices in question.answerChoices"
                    :key="choices"
                  >
                    <label v-for="(v, k) in choices" :key="k">
                      &emsp;&emsp;&nbsp;&nbsp;<b>{{ k }}:</b> {{ v }} <br />
                    </label> </label
                  ><br />
                  &emsp;&nbsp; <b>Required:</b>
                  <label>{{ question.required }}</label
                  ><br />
                  <button type="button" class="btn btn-success" @click="updateQuestion(question)" data-bs-toggle="modal" data-bs-target="#updateModal" style="margin-left: 20px">
                    Update
                  </button>
                  <button
                    type="button"
                    class="btn btn-danger"
                    @click="deleteQuestion(question.questionId)"
                    style="margin-left: 20px"
                  >
                    Delete
                  </button>
                  <br /><br />
                </div>
              </div>
              <button
              type="button"
              @click="this.showSelected = !this.showSelected">
              Show Selected Questions
              </button>
              <ul class="list-group" v-if="this.showSelected==true">
                <li  class="list-group-item" v-for="question of this.selectedQuestion" :key="question">{{question.questionText }}</li>
              </ul>

              <button
                type="button"
                class="btn btn-danger"
                @click="addQuestion"
                data-bs-toggle="modal"
                data-bs-target="#exampleModal"
              >
                Add New Question
              </button>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="btn-group" role="submitChange">
        <button type="button" class="btn btn-secondary" @click="updateForm">
          Update
        </button>
      </div>
    </div>

    <!-- Modal to Add Question -->
    <div
      class="modal fade"
      id="exampleModal"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Add Question</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <!-- 
                    {
                        "questionText": "Some safety questions 1",
                        "questionType": "textbox",
                        "questionSectionName": "Safety",
                        "answerChoices" : [{"inputName": "True", "inputValue": "1"}, {"inputName": "False", "inputValue": "0"}],
                        "required": true
                    }
                -->
            <table>
              <tr>
                <td>Question Text:</td>
                <td><input type="text" v-model="questionText" /></td>
              </tr>
              <tr>
                <td>Question Type:</td>
                <td>
                  <select
                    style="width: 100%"
                    name="selectRole"
                    id="selectRole"
                    @change="onChange($event)"
                  >
                    <option
                      v-for="item in questionTypeArr"
                      :key="item"
                      v-bind:value="item"
                    >
                      {{ item }}
                    </option>
                  </select>
                </td>
              </tr>
              <tr>
                <td>Question Selection Name (Group):</td>
                <td><input type="text" v-model="questionSectionName" /></td>
              </tr>
              <tr>
                <td v-if="questionType !== 'text'" class="controls">
                  Answer Choices:
                </td>
                <td>
                  <div id="answers"></div>
                  <div v-if="questionType !== 'text'" class="controls">
                    <button
                      type="button"
                      id="add_more_fields"
                      class="btn btn-primary"
                      @click="addAnswer"
                    >
                      Add Answers
                    </button>
                    <button
                      type="button"
                      id="remove_fields"
                      class="btn btn-warning"
                      @click="removeAnswer"
                    >
                      Remove Answers
                    </button>
                  </div>
                </td>
              </tr>
              <tr>
                <td>Required:</td>
                <td><input type="checkbox" v-model="required" /></td>
              </tr>
            </table>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              id="closeModal"
              data-bs-dismiss="modal"
            >
              Close
            </button>
            <button
              type="button"
              class="btn btn-primary"
              @click="addNewQuestion"
            >
              Add
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal to Edit Question -->
    <div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="updateModal" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="updateModalLabel">Update Question</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <table>
              <tr>
                <td>Question Text:</td>
                <td><input type="text" v-model="questionText" /></td>
              </tr>
              <tr>
                <td>Question Type:</td>
                <td>
                  <select style="width: 100%" name="selectRole" id="selectRole2" @change="onUpdateChange($event)">
                    
                    <option v-for="item in questionTypeArr" :key="item" v-bind:value="item">
                      {{ item }}
                    </option>

                  </select>
                </td>
              </tr>

              <tr>
                <td>Question Selection Name (Group):</td>
                <td><input type="text" v-model="questionSectionName" /></td>
              </tr>

              <tr>
                <td v-if="updateQuestionType !== 'text'" class="controls">
                  Answer Choices:
                </td>

                <td>
                  <div id="answers2"></div>
                  <div v-if="updateQuestionType !== 'text'" class="controls">
                    <button type="button" id="add_more_fields" class="btn btn-primary" @click="addAnswer2">
                      Add Answers
                    </button>

                    <button type="button" id="remove_fields" class="btn btn-warning" @click="removeAnswer2">
                      Remove Answers
                    </button>

                  </div>
                </td>
              </tr>
              <tr>
                <td>Required:</td>

                <td><input type="checkbox" v-model="required" /></td>

              </tr>
            </table>

          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" id="closeModal2" data-bs-dismiss="modal">
              Close
            </button>

            <button type="button" class="btn btn-primary" @click="updateNewQuestion" >
              Update
            </button>

          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
// import axios from 'axios';

import axios from "axios";

export default {
  data() {
    return {
      data: JSON.parse(localStorage.editForm),
      userType: localStorage.userType,
      updateQuestionId: "",
      selectedQuestion: [],
      allQuestions: [],
      lastEdited: "",
      dateSubmitted: "",
      answerArray: null,
      answersArray2: null,
      questionText: "",
      updateQuestionType: "text",
      questionType: "text",
      questionSectionName: "",
      answerChoices: [],
      required: false,
      questionTypeArr: ["text", "radio", "checkbox"],
      showSelected: false,
    };
  },
  methods: {
    updateForm() {
      if (this.userType === "ROLE_ADMIN" || this.userType === "ROLE_APPROVER") {
        console.log(this.selectedQuestion);
        console.log(this.formNo);
        console.log(this.formName);
        console.log(this.revisionNo);
        console.log(this.formatDate(this.lastEdited));
        console.log(this.dateSubmitted);

        //Create a json object like this
        /*
                {
                    "formNo": "Q222",
                    "formName": "test form ahhhhh",
                    "revisionNo": 1,
                    "lastEdited": "20/3/2023",
                    "dateSubmitted": "21/3/2023",
                    "questions": [
                        {
                            "questionText": "rate from 1 to 5",
                            "questionType": "mcq",
                            "answerChoices": null,
                            "required": true
                        },
                        {
                            "questionId": "6419bcb46448af17bc12a86d",
                            "questionText": "test5",
                            "questionType": "mcq",
                            "isRequired": true
                        }
                    ]
                }
                */
        var json = "{";
        json += '"formNo": "' + this.data.formNo + '",';
        json += '"formName": "' + this.data.formName + '",';
        json += '"revisionNo": "' + this.data.revisionNo + '",';
        json +=
          '"lastEdited": "' + this.formatDate(this.data.lastEdited) + '",';
        json += '"template": ' + true + ",";

        json += '"questions": [';
        for (var i = 0; i < this.selectedQuestion.length; i++) {
          json += "{";
          json +=
            '"questionId": "' + this.selectedQuestion[i].questionId + '",';
          json +=
            '"questionText": "' + this.selectedQuestion[i].questionText + '",';
          json +=
            '"questionType": "' + this.selectedQuestion[i].questionType + '",';
          json += '"isRequired": ' + this.selectedQuestion[i].required;
          json += "}";
          if (i < this.selectedQuestion.length - 1) {
            json += ",";
          }
        }
        json += "]}";

        console.log("==================================");

        console.log(json);

        json = JSON.parse(json);

        console.log(json);

        console.log("==================================");

        axios
          .put(
            "http://localhost:8080/api/forms/update/" + this.data.formId,
            json,
            {
              headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + localStorage.token,
                "Access-Control-Allow-Origin": "*",
              },
            }
          )
          .then((response_users) => {
            console.log(response_users);

            this.$router.push({ name: "ViewForm" });

            alert("Form updated successfully");
          });
      }
    },
    getQuestionsData() {
      if(this.data.template){
        axios
        .get("http://localhost:8080/api/form-builder/all_templates", {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.token,
            "Access-Control-Allow-Origin": "*",
          },
        })
        .then((response) => {
          this.allQuestions = response.data;
          //this.isQuestionDisabled = false;
          console.log(this.allQuestions);
        });
      }
      else{
        axios
        .get("http://localhost:8080/api/form-builder/form_edit_qns/" + this.data.formId, {
          headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.token,
            "Access-Control-Allow-Origin": "*",
          },
        })
        .then((response) => {
          this.allQuestions = response.data;
          
          // disable checkbox for questions that are already linked to workflow
          this.isQuestionDisabled = true;
        });
      }
    },
    addAnswer() {
      console.log("adding answer");

      var inputNameField = document.createElement("input");
      inputNameField.setAttribute("type", "text");
      inputNameField.setAttribute("name", "answersArray[]");
      inputNameField.setAttribute("siz", 50);
      inputNameField.setAttribute("placeholder", "Input Name");
      this.answerArray.appendChild(inputNameField);

      var inputValueField = document.createElement("input");
      inputValueField.setAttribute("type", "text");
      inputValueField.setAttribute("name", "answersArray[]");
      inputValueField.setAttribute("siz", 50);
      inputValueField.setAttribute("placeholder", "Input Value");
      this.answerArray.appendChild(inputValueField);

      this.answerArray.appendChild(document.createElement("br"));
      this.answerArray.appendChild(document.createElement("br"));
    },
    updateQuestion(question) {
      console.log(question);
      this.answerArray2 = document.getElementById("answers2");
      this.updateQuestionId = question.questionId;
      this.questionText = question.questionText;
      this.questionSectionName = question.questionSectionName;
      document.getElementById("selectRole2").value = question.questionType;
      this.updateQuestionType = question.questionType;
      this.required = question.required;

      for(var i in question.answerChoices)
      {
        var answerObj = question.answerChoices[i];
        console.log(answerObj);

        var inputNameField = document.createElement("input");
        inputNameField.setAttribute("type", "text");
        inputNameField.setAttribute("name", "answersArray2[]");
        inputNameField.setAttribute("siz", 50);
        inputNameField.setAttribute("placeholder", "Input Name");
        inputNameField.setAttribute("value", answerObj.inputName);
        this.answerArray2.appendChild(inputNameField);

        var inputValueField = document.createElement("input");
        inputValueField.setAttribute("type", "text");
        inputValueField.setAttribute("name", "answersArray2[]");
        inputValueField.setAttribute("siz", 50);
        inputValueField.setAttribute("placeholder", "Input Value");
        inputValueField.setAttribute("value", answerObj.inputValue);
        this.answerArray2.appendChild(inputValueField);

        this.answerArray2.appendChild(document.createElement("br"));
        this.answerArray2.appendChild(document.createElement("br"));        
      }
    },
    addAnswer2() {
      console.log("adding answer2");
      var inputNameField = document.createElement("input");
      inputNameField.setAttribute("type", "text");
      inputNameField.setAttribute("name", "answersArray2[]");
      inputNameField.setAttribute("siz", 50);
      inputNameField.setAttribute("placeholder", "Input Name");
      this.answerArray2.appendChild(inputNameField);

      var inputValueField = document.createElement("input");
      inputValueField.setAttribute("type", "text");
      inputValueField.setAttribute("name", "answersArray2[]");
      inputValueField.setAttribute("siz", 50);
      inputValueField.setAttribute("placeholder", "Input Value");
      this.answerArray2.appendChild(inputValueField);

      this.answerArray2.appendChild(document.createElement("br"));
      this.answerArray2.appendChild(document.createElement("br"));

    },
    removeAnswer2() {
      console.log("remove answer2");
      var input_tags = this.answerArray2.getElementsByTagName("input");

      var br_tags = this.answerArray2.getElementsByTagName("br");
      if (input_tags.length > 0) {
        this.answerArray2.removeChild(input_tags[input_tags.length - 1]);
        this.answerArray2.removeChild(input_tags[input_tags.length - 1]);

        this.answerArray2.removeChild(br_tags[br_tags.length - 1]);
        this.answerArray2.removeChild(br_tags[br_tags.length - 1]);
      }
    },
    updateNewQuestion()
    {
      console.log("Updating new question");

      var questionId = this.updateQuestionId;

      //the following is a document.html tag
      var tempAnswerArray = document.getElementsByName("answersArray2[]");

      var tempAnswerArray2 = [];
      for (var i = 0; i < tempAnswerArray.length; i++) {
        tempAnswerArray2.push(tempAnswerArray[i].value);
      }

      this.answerChoices = [];

      for(var x = 0; x < tempAnswerArray2.length; x+=2)
            {
                var tempObject = "{";
                tempObject += "\"" + "inputName" + "\": \"" + tempAnswerArray2[x] + "\",";
                tempObject += "\"" + "inputValue" + "\": \"" + tempAnswerArray2[x+1] + "\"";
                tempObject += "}";
                
                if(x < tempAnswerArray2.length)
                {
                    this.answerChoices.push(tempObject);
                }
            }

      /*
                    {
                        "questionText": "Some safety questions 1",
                        "questionType": "textbox",
                        "questionSectionName": "Safety",
                        "answerChoices" : [{"inputName": "True", "inputValue": "1"}, {"inputName": "False", "inputValue": "0"}],
                        "required": true
                    }
            */

      if (this.answerChoices.length == 0) {
        this.answerChoices = null;
      }

      var tempChoices = "";
      if (this.answerChoices == null) {
        tempChoices = '"answerChoices": ' + this.answerChoices + ",";
      } else {
        tempChoices = '"answerChoices": [' + this.answerChoices + "],";
      }

      var createQuestion = "{";
      createQuestion += '"questionText": "' + this.questionText + '",';
      createQuestion += '"questionType": "' + this.updateQuestionType + '",';
      createQuestion +=
        '"questionSectionName": "' + this.questionSectionName + '",';
      createQuestion += tempChoices;
      createQuestion += '"required": ' + this.required;
      createQuestion += "}";

      console.log("THIS IS UPDATING QUESTIONS");
      createQuestion = JSON.parse(createQuestion);
      console.log(createQuestion);

      axios
        .put(
          "http://localhost:8080/api/form-builder/edit-question/" + questionId,
          createQuestion,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + localStorage.token,
              "Access-Control-Allow-Origin": "*",
            },
          }
        )
        .then((response_question) => {
          console.log(response_question);

          this.questionData = [];
          this.getQuestionsData();
          document.getElementById("closeModal2").click();
        });

    },

    removeAnswer() {
      console.log("remove answer");
      var input_tags = this.answerArray.getElementsByTagName("input");

      var br_tags = this.answerArray.getElementsByTagName("br");
      if (input_tags.length > 0) {
        this.answerArray.removeChild(input_tags[input_tags.length - 1]);
        this.answerArray.removeChild(input_tags[input_tags.length - 1]);

        this.answerArray.removeChild(br_tags[br_tags.length - 1]);
        this.answerArray.removeChild(br_tags[br_tags.length - 1]);
      }
    },
    addQuestion() {
      console.log("add question");
      this.answerArray = document.getElementById("answers");
      console.log(this.answerArray);
    },
    deleteQuestion(questionId) {
      console.log("delete question: " + questionId);

      axios
        .delete("http://localhost:8080/api/form-builder/delete/" + questionId, {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.token,
            "Access-Control-Allow-Origin": "*",
          },
        })
        .then((response) => {
          console.log(response.status);
          if (response.status == 200) {
            alert("Question deleted successfully");
            this.getQuestionsData();
          } else {
            alert("Err: question deletion failed");
          }
        });
    },
    formatDate(input) {
      var datePart = input.match(/\d+/g),
        year = datePart[0], // get only two digits
        month = datePart[1],
        day = datePart[2];

      return day + "/" + month + "/" + year;
    },
    getCurrentDate() {
      var today = new Date();
      var year = today.getFullYear();
      var month = parseInt(today.getMonth()) + 1;
      var day = today.getDate();

      //convert month to String
      if (month < 10) {
        month = "0" + month;
      }
      var fullDate = year + "-" + month + "-" + day;
      return fullDate;
    },
    onChange(event) {
      console.log(event.target.value);
      this.questionType = event.target.value;
    },
    addNewQuestion() {
      //the following is a document.html tag
      var tempAnswerArray = document.getElementsByName("answersArray[]");

      var tempAnswerArray2 = [];
      for (var i = 0; i < tempAnswerArray.length; i++) {
        tempAnswerArray2.push(tempAnswerArray[i].value);
      }

      this.answerChoices = [];

      for (var x = 0; x < tempAnswerArray2.length; x += 2) {
        var tempObject = "{";
        tempObject += '"' + "inputName" + '": "' + tempAnswerArray2[x] + '",';
        tempObject +=
          '"' + "inputValue" + '": "' + tempAnswerArray2[x + 1] + '"';
        tempObject += "}";

        if (x < tempAnswerArray2.length) {
          this.answerChoices.push(tempObject);
        }
      }

      /*
                    {
                        "questionText": "Some safety questions 1",
                        "questionType": "textbox",
                        "questionSectionName": "Safety",
                        "answerChoices" : [{"inputName": "True", "inputValue": "1"}, {"inputName": "False", "inputValue": "0"}],
                        "required": true
                    }
            */

      if (this.answerChoices.length == 0) {
        this.answerChoices = null;
      }

      var tempChoices = "";
      if (this.answerChoices == null) {
        tempChoices = '"answerChoices": ' + this.answerChoices + ",";
      } else {
        tempChoices = '"answerChoices": [' + this.answerChoices + "],";
      }

      var createQuestion = "{";
      createQuestion += '"questionText": "' + this.questionText + '",';
      createQuestion += '"questionType": "' + this.questionType + '",';
      createQuestion +=
        '"questionSectionName": "' + this.questionSectionName + '",';
      createQuestion += tempChoices;
      createQuestion += '"required": ' + this.required;
      createQuestion += "}";

      createQuestion = JSON.parse(createQuestion);
      console.log(createQuestion);

      axios
        .post(
          "http://localhost:8080/api/form-builder/add-question",
          createQuestion,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + localStorage.token,
              "Access-Control-Allow-Origin": "*",
            },
          }
        )
        .then((response_question) => {
          console.log(response_question);

          this.questionData = [];
          this.getQuestionsData();
          document.getElementById("closeModal").click();
        });
    },
  },
  created() {
    // console.log(this.data);
    this.selectedQuestion = this.data.questions;
    console.log(this.data.questions);
    this.lastEdited = this.getCurrentDate();
    this.dateSubmitted = this.getCurrentDate();
    // console.log(this.selectedQuestions);
    try {
      this.getQuestionsData();
    } catch (e) {
      if (e instanceof SyntaxError) {
        this.$router.push({ name: "Login" });
      } else {
        console.log(e);
      }
    }
  },
};
</script>
