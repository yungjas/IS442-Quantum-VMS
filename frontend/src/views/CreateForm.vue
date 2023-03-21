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
    <h1>Create a Form</h1>
    <div class="btn-group" role="currentUser" >
        <button type="button" class="btn btn-secondary" @click="home">Home</button>
        <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
    </div>
    <br><br>

    <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
            <table class="table">
                <tbody>
                    <tr>
                        <td>
                            <label>Form No</label>
                        </td>
                        <td>                            
                            <input type=text v-model="formNo" style="width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>formName</label>
                        </td>
                        <td>
                            <input type=text v-model="formName" style="width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>revisionNo</label>
                        </td>
                        <td>
                            <input type=text v-model="revisionNo" style="width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>LastEdited</label>
                        </td>
                        <td>
                            <input type=date v-model="lastEdited" style="width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>Date Submitted</label>
                        </td>
                        <td>
                            <input type=date v-model="dateSubmitted" style="width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>Questions</label>
                        </td>
                        <td style="text-align: left;">


                            <div v-for="question in questionData" :key="question.questionId">
                                <input type="checkbox" v-model="selectedQuestion" :value="question"/> &nbsp;
                                <b>Question Text:</b> <label>{{ question.questionText }}</label><br> &emsp;&nbsp;
                                <b>Question Type:</b> <label>{{ question.questionType }}</label><br> &emsp;&nbsp;
                                <b>Answer Choices:</b> <label>{{ question.answerChoices }}</label><br> &emsp;&nbsp;
                                <b>Required:</b> <label>{{ question.required }}</label><br>
                                ================================
                            </div>
                            

                            <!-- <select v-model="selected" style="width: 100%">
                                <option v-for="question in questionData" v-bind:key="question.questionId">
                                    {{ question.questionText }}
                                </option>
                            </select> -->
                        </td>
                    </tr>
                </tbody>
            </table>
    
            <div class="btn-group" role="submitChange">
                <button type="button" class="btn btn-secondary" @click="createForm">Create</button>
            </div>
        </div>    
</template>


<script>
import axios from "axios";

export default {
    name: 'CreateForm',
    data () {
        return {
            questionData: [],
            userType: localStorage.userType,
            selectedQuestion: [],
            formNo: '',
            formName: '',
            revisionNo: '',
            lastEdited: '',
            dateSubmitted: '',
        }
    },
    methods: 
    {
        home: function()
        {
            this.$router.push({name: 'Home'});
        },
        logout: function()
        {
            localStorage.clear();
            this.$router.push({name: 'Login'});
        },
        createForm: function()
        {
            if(this.userType === 'ROLE_ADMIN' || this.userType === 'ROLE_APPROVER')
            {
                
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
                json += "\"formNo\": \"" + this.formNo + "\",";
                json += "\"formName\": \"" + this.formName + "\",";
                json += "\"revisionNo\": \"" + this.revisionNo + "\",";
                json += "\"lastEdited\": \"" + this.formatDate(this.lastEdited) + "\",";
                json += "\"dateSubmitted\": \"" + this.formatDate(this.dateSubmitted) + "\",";
                json += "\"questions\": [";
                for(var i = 0; i < this.selectedQuestion.length; i++)
                {
                    json += "{";
                    json += "\"questionId\": \"" + this.selectedQuestion[i].questionId + "\",";
                    json += "\"questionText\": \"" + this.selectedQuestion[i].questionText + "\",";
                    json += "\"questionType\": \"" + this.selectedQuestion[i].questionType + "\",";
                    json += "\"isRequired\": " + this.selectedQuestion[i].required;
                    json += "}";
                    if(i < this.selectedQuestion.length - 1)
                    {
                        json += ",";
                    }
                }
                json += "]}";

                json = JSON.parse(json);
                
                console.log(json);

                axios.post("http://localhost:8080/api/forms/create", json, {
                                headers:{
                                    "Content-Type": "application/json",
                                    "Authorization": "Bearer " + localStorage.token,
                                    "Access-Control-Allow-Origin": "*",
                                }
                            })
                            .then((response_users) => {
                                console.log(response_users);

                                this.$router.push({name: 'Home'});
                                        
                                alert("Form created successfully");
                            })                                          


            }
        },
        getQuestionsData()
        {
            axios.get("http://localhost:8080/api/form-builder/all", 
            {
                headers:{
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + localStorage.token,
                    "Access-Control-Allow-Origin": "*",
                }
                })
                .then((response) => {

                    this.questionData = response.data;
                    console.log(this.questionData);
            })                                    
        },
        formatDate (input) {
        var datePart = input.match(/\d+/g),
        year = datePart[0], // get only two digits
        month = datePart[1], day = datePart[2];

        return day+'/'+month+'/'+year;
        },
        getCurrentDate()
        {
            var today = new Date();
            var year = today.getFullYear();
            var month = parseInt(today.getMonth())+1;
            var day = today.getDate();

            //convert month to String
            if(month < 10)
            {
                month = '0' + month;
            }
            var fullDate = year + '-' + month + '-' + day;
            return fullDate;
        },
    },
    created()
    {
        this.getQuestionsData();
        //get today date

        this.lastEdited = this.getCurrentDate();
        this.dateSubmitted = this.getCurrentDate();
    },
}
</script>
