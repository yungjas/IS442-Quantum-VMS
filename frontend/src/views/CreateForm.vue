<!-- 

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

-->

<template>
  <div style="margin-top: 2em">
    <h1>Create a Form Template</h1>
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
              <input type="text" v-model="formNo" style="width: 100%" />
            </td>
          </tr>

          <tr>
            <td>
              <label>Form Name</label>
            </td>

            <td>
              <input type="text" v-model="formName" style="width: 100%" />
            </td>
          </tr>

          <tr>
            <td>
              <label>Revision No.</label>
            </td>

            <td>
              <input type="text" v-model="revisionNo" style="width: 100%" />
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
          <tr style="width: 100%">
            <td></td>
            <td>
              <input
                id="search"
                type="text"
                class="input"
                v-model="input"
                @change="handleSearch"
                @keyup="handleSearch"
                style="width: 100%"
                placeholder="Search Question"
              />
            </td>
          </tr>

          <!-- <tr style="width: 100%" > 
                <td>

                </td>
                <td style="text-align:left;">
                    <div>
                      <p> {{ noQuestion }} </p>
                    </div>
                </td>
            </tr> -->

          <tr>
            <td>
              <label>Questions</label>
            </td>

            <td style="text-align: left">
              <div v-if="filteredList && input">
                <div
                  style="text-align: left; margin-top: 20px"
                  class="card"
                  v-for="question in paginatedFilteredData"
                  :key="question.questionId"
                >
                  <div class="cardbody" style="padding: 10px">
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

                    &emsp;&nbsp; <b>Answer Choices:</b> <br />
                    <label
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

                    <br />

                    <button
                      type="button"
                      class="btn btn-success"
                      @click="updateQuestion(question)"
                      data-bs-toggle="modal"
                      data-bs-target="#updateModal"
                      style="margin-left: 20px"
                    >
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

                    <br />
                  </div>
                </div>
              </div>

              <div v-else>
                <div
                  v-for="question in paginatedQuestionData"
                  :key="question.questionId"
                  class="card"
                  style="margin-top: 20px"
                >
                  <div class="cardbody" style="padding: 10px">
                    <input
                      type="checkbox"
                      v-model="selectedQuestion"
                      :value="question"
                    />
                    &nbsp; <b>Question Text:</b>
                    <label>{{ question.questionText }}</label>
                    <br />

                    &emsp;&nbsp; <b>Question Type:</b>
                    <label>{{ question.questionType }}</label>
                    <br />

                    &emsp;&nbsp; <b>Question Section Name:</b>
                    <label>{{ question.questionSectionName }}</label>
                    <br />

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
                    <label>{{ question.required }}</label>

                    <br />

                    <button
                      type="button"
                      class="btn btn-success"
                      @click="updateQuestion(question)"
                      data-bs-toggle="modal"
                      data-bs-target="#updateModal"
                      style="margin-left: 20px"
                    >
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

                    <br />
                  </div>
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <div>
        <button
          type="button"
          class="btn btn-danger"
          @click="addQuestion"
          data-bs-toggle="modal"
          data-bs-target="#exampleModal"
        >
          Add New Question
        </button>
      </div>

      <br />

      <button
        class="btn btn-primary"
        type="button"
        @click="this.showSelected = !this.showSelected"
        style="margin-bottom: 10px;"
      >
        Show Selected Questions
      </button>

      <ul
        style="margin-top: 20px; margin-bottom: 20px"
        class="list-group"
        v-if="this.showSelected == true"
      >
        <li
          class="list-group-item"
          v-for="question in this.selectedQuestion"
          :key="question.questionId"
        >
          {{ question.questionText }}
        </li>
      </ul>

      <br />

      <div v-if="filteredList && input">
        <div class="font-weight-bold">
          <a href="#search">
          <button
            type="button"
            class="btn btn-secondary"
            v-if="filteredPrevPage"
            @click="prevPage"
          >
            Prev
          </button>
          </a>

          Page {{ currentPage }} of {{ filteredPages }}
          <a href="#search">
          <button
            type="button"
            class="btn btn-secondary"
            v-if="filteredNextPage"
            @click="nextPage"
          >
            Next
          </button>
        </a>
        </div>
      </div>

      <div v-else>
        <div class="font-weight-bold">
          <a href="#search">
            <button
              type="button"
              class="btn btn-secondary"
              v-if="hasPrevPage"
              @click="prevPage"
            >
              Prev
            </button>
          </a>

          Page {{ currentPage }} of {{ totalPages }}

          <a href="#search"
            ><button
              type="button"
              class="btn btn-secondary"
              v-if="hasNextPage"
              @click="nextPage"
            >
              Next
            </button>
          </a>
        </div>
      </div>

      <br /><br />

      <div
        class="btn-group justify-content-end align-items-end"
        role="submitChange"
      >
        <button type="button" class="btn btn-secondary" @click="createForm">
          Create
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
    <div
      class="modal fade"
      id="updateModal"
      tabindex="-1"
      aria-labelledby="updateModal"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="updateModalLabel">Update Question</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
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
                  <select
                    style="width: 100%"
                    name="selectRole"
                    id="selectRole2"
                    @change="onUpdateChange($event)"
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
                <td v-if="updateQuestionType !== 'text'" class="controls">
                  Answer Choices:
                </td>

                <td>
                  <div id="answers2"></div>
                  <div v-if="updateQuestionType !== 'text'" class="controls">
                    <button
                      type="button"
                      id="add_more_fields"
                      class="btn btn-primary"
                      @click="addAnswer2"
                    >
                      Add Answers
                    </button>

                    <button
                      type="button"
                      id="remove_fields"
                      class="btn btn-warning"
                      @click="removeAnswer2"
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
              id="closeModal2"
              data-bs-dismiss="modal"
            >
              Close
            </button>

            <button
              type="button"
              class="btn btn-primary"
              @click="updateNewQuestion"
            >
              Update
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CreateForm",
  data() {
    return {
      questionData: [],
      updateQuestionId: "",
      userType: localStorage.userType,
      selectedQuestion: [],
      formNo: "",
      formName: "",
      revisionNo: "",
      lastEdited: "",
      dateSubmitted: "",
      answerArray: null,
      answerArray2: null,
      questionText: "",
      questionType: "text",
      updateQuestionType: "text",
      questionSectionName: "",
      answerChoices: [],
      required: false,
      questionTypeArr: ["text", "radio", "checkbox"],
      pageSize: 5, // number of items per page
      currentPage: 1, // current page number
      filteredList: [],
      input: "",
      showSelected: false,
      // noQuestion: "",
    };
  },
  computed: {
    paginatedQuestionData() {
      // calculate start and end index of current page
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      console.log(this.questionData.slice(startIndex, endIndex));
      // return items for current page
      return this.questionData.slice(startIndex, endIndex);
    },
    totalPages() {
      // calculate total number of pages
      return Math.ceil(this.questionData.length / this.pageSize);
    },
    hasPrevPage() {
      // return true if current page is not the first page
      return this.currentPage > 1;
    },
    hasNextPage() {
      // return true if current page is not the last page
      return this.currentPage < this.totalPages;
    },
    paginatedFilteredData() {
      // calculate start and end index of current page
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      console.log(this.filteredList.slice(startIndex, endIndex));
      // return items for current page
      return this.filteredList.slice(startIndex, endIndex);
    },
    filteredPages() {
      // calculate total number of pages
      return Math.ceil(this.filteredList.length / this.pageSize);
    },
    filteredPrevPage() {
      // return true if current page is not the first page
      return this.currentPage > 1;
    },
    filteredNextPage() {
      // return true if current page is not the last page
      return this.currentPage < this.filteredPages;
    },
  },
  methods: {
    home: function () {
      this.$router.push({ name: "Home" });
    },
    logout: function () {
      localStorage.clear();
      this.$router.push({ name: "Login" });
    },
    createForm: function () {
      if (this.userType === "ROLE_ADMIN" || this.userType === "ROLE_APPROVER") {
        console.log("HellO");
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
        json += '"formNo": "' + this.formNo + '",';
        json += '"formName": "' + this.formName + '",';
        json += '"revisionNo": "' + this.revisionNo + '",';
        json += '"lastEdited": "' + this.formatDate(this.lastEdited) + '",';
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
          .post("http://localhost:8080/api/forms/create_template", json, {
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + localStorage.token,
              "Access-Control-Allow-Origin": "*",
            },
          })
          .then((response_users) => {
            console.log(response_users);

            this.$router.push({ name: "ViewForm" });

            alert("Form created successfully");
          });
      }
    },

    handleSearch() {
      console.log(this.input);
      if (this.input && this.input.length > 0) {
        this.filteredList = this.questionData.filter((question) => {
          const input = this.input.toLowerCase();
          const questionName = question.questionText.toLowerCase();
          if (input && questionName.indexOf(input) !== -1) {
            console.log(this.filteredList);
            return this.filteredList;
          }
          //  else {
          //     this.noQuestion = "No Such Question";
          //  }
        });
      }
    },
    getQuestionsData() {
      axios
        .get("http://localhost:8080/api/form-builder/all_templates", {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.token,
            "Access-Control-Allow-Origin": "*",
          },
        })
        .then((response) => {
          this.questionData = response.data;
          console.log(this.questionData);
        });
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
    addQuestion() {
      console.log("add question");
      this.answerArray = document.getElementById("answers");
      console.log(this.answerArray);
      this.questionText = null;
      this.questionSectionName = null;
      this.required = false;
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

      for (var i in question.answerChoices) {
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
    updateNewQuestion() {
      console.log("Updating new question");

      var questionId = this.updateQuestionId;

      //the following is a document.html tag
      var tempAnswerArray = document.getElementsByName("answersArray2[]");

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
    onUpdateChange(event) {
      console.log(event.target.value);
      this.updateQuestionType = event.target.value;
    },
    prevPage() {
      // move to previous page
      this.currentPage--;
    },
    nextPage() {
      // move to next page
      this.currentPage++;
    },
  },
  created() {
    this.getQuestionsData();
    //get today date

    this.lastEdited = this.getCurrentDate();
    this.dateSubmitted = this.getCurrentDate();
  },
};
</script>

<style>
.input {
  padding: 10px 45px;
  background: white url("../assets/search.svg") no-repeat 15px center;
  background-size: 15px 15px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px,
    rgba(0, 0, 0, 0.3) 0px 1px 3px -1px;
}
</style>
