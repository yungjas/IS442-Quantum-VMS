<template>
 
  <div>
    <h1>Form</h1>
    <div id="forms" class="px-5">
      
    </div>   
    <button id="btnSubmitForm" class="btn btn-primary" @click="submitForm()" hidden>Submit/Save Form</button> &nbsp;
    <button id="btnGeneratePDF" class="btn btn-success" @click="generatePDF()" hidden>Generate PDF</button> &nbsp;
    <button id="btnApproveForm" class="btn btn-success" @click="approveForm()" hidden>Approve Form</button>
    <button id="btnUnapproveForm" class="btn btn-danger" @click="unapproveForm()" hidden>Unapprove Form</button>
    <p id="formStatus"></p>
  </div>
  
</template>


<script>
import axios from "axios";
import html2pdf from "html2pdf.js";

export default {
  name: 'ShowWorkflowForm',
  data () {
      return {
        formId: localStorage.formId,
        userId: localStorage.userId,
        jsonData: null,        
        btnSubmitForm: null,
        btnGeneratePDF: null,
        btnApprove: null,
        btnUnapprove: null,
        formStatus: null,
        form: null,
        approved: false,
        placeholderID: 0,
        submittedJSONData: null,
        formObj: null,
        userObj: null,
        qnsObj: null,
        counter: 0,
        userResponseId: null,
        isApprove: false,
      }
  },
  methods: 
  {
    approveForm()
    {
      var approveFormObj = this.jsonData.form;
      axios.put("http://localhost:8080/api/forms/approve/" + approveFormObj.formId, approveFormObj, {
                                  headers:{
                                      "Content-Type": "application/json",
                                      "Authorization": "Bearer " + localStorage.token,
                                      "Access-Control-Allow-Origin": "*",
                                  }
                              })
                              .then((response) => {
                                  alert("Form has been approved")
                                  console.log(response);
                                  this.$router.push("/viewWorkflow");
                              })
    },
    unapproveForm()
    {
      var approveFormObj = this.jsonData.form;
      axios.put("http://localhost:8080/api/forms/unapprove/" + approveFormObj.formId, approveFormObj, {
                                  headers:{
                                      "Content-Type": "application/json",
                                      "Authorization": "Bearer " + localStorage.token,
                                      "Access-Control-Allow-Origin": "*",
                                  }
                              })
                              .then((response) => {
                                  console.log(response);
                                  this.$router.push("/viewWorkflow");
                              })
    },
    generatePDF()
    {
      console.log("generating pdf");
      html2pdf(this.form, {
        margin: 1,
        filename: "form.pdf",
      });
    },
    submitForm()
    {
      console.log("submitting form");

      this.submittedJSONData = "{";

      for(var i = 0; i < this.placeholderID; i++)
      {
        // console.log("\n\n")        
        // console.log(i);

        var element = document.getElementsByClassName(i);
        var qnsId = "";
        if(element !== null)
        {
          var questionResponse = [];
          for(var j = 0; j < element.length; j++)
          {
            if(element[j].id !== "")
            {
              qnsId = element[j].id;
            }

            var noResponse = element[j].className.split(" ").includes("noResponse");

            if(qnsId !== "")
            {
              // console.log("qnsId: " + qnsId);
              if(element[j].type === "text" && element[j].nodeName !== "LABEL")
              {
                // console.log(element[j].nodeName)
                var value = element[j].value;
                questionResponse.push({ "inputName": "text", "inputValue": value });
                // console.log(questionResponse);
                
              }
              else if(element[j].type === "radio" && element[j].nodeName !== "LABEL")
              {
                // console.log(element[j].nodeName)
                var value2 = element[j];
                if(value2.checked)
                {
                  questionResponse.push({ "inputName": "radio", "inputValue": value2.value });
                }
                // console.log(questionResponse);
                
              }
              else if(element[j].type === "checkbox" && element[j].nodeName !== "LABEL")
              {
                // console.log(element[j].nodeName)
                var value3 = element[j];
                if(value3.checked)
                {
                  questionResponse.push({ "inputName": "checkbox", "inputValue": value3.value });
                }
                // console.log(questionResponse);
                
              }
            }
          }
          this.addUserResponse(qnsId, questionResponse, noResponse);
        }
      }
    },
    addUserResponse(qnsId, questionResponse, noResponse)
    {

      var userId = this.userId;
      var formId = this.formId;
      if(questionResponse.length === 0)
      {
        questionResponse = null;
      }

      var json = {
        "userId": userId,
        "formId": formId,
        "qnId": qnsId,
        "questionResponse": questionResponse
      }

      console.log(json);
      
      if(noResponse)
      {
        //CREATE
        // console.log(++this.counter);
        // console.log(json);
        axios.post("http://localhost:8080/api/response/create", json, {
                                  headers:{
                                      "Content-Type": "application/json",
                                      "Authorization": "Bearer " + localStorage.token,
                                      "Access-Control-Allow-Origin": "*",
                                  }
                              })
                              .then((response) => {
                                  console.log(response);
                                  this.formStatus.innerHTML = "Submitted";
                                  alert("Saved Successfully");
                              })

      }
      else if(!noResponse)
      {
        //UPDATE
        console.log("update");
        console.log(json);
        console.log(this.userResponseId);
        
        axios.put("http://localhost:8080/api/response/update/" + formId + "/" + qnsId, json, {
                                  headers:{
                                      "Content-Type": "application/json",
                                      "Authorization": "Bearer " + localStorage.token,
                                      "Access-Control-Allow-Origin": "*",
                                  }
                              })
                              .then((response) => {
                                  console.log(response);
                                  console.log(this.formStatus);
                                  this.formStatus.innerHTML = "Update Successfully";
                              })                
      }


      
    },
    showForm()
    {
        
      // console.log("populating form\n=========================")
      // console.log("adding answer");

      // console.log(this.jsonData);
      var formObj = this.jsonData.form;

      // console.log("=========================")
      // console.log("formObj:")
      // console.log(formObj);        

      if(formObj !== null)
      {
        this.btnSubmitForm.hidden = false;

        var table = document.createElement("table");

        console.log(this.jsonData)
        this.approved = formObj.approved;
        if(this.approved)
        {
          this.btnGeneratePDF.hidden = false;
          this.btnSubmitForm.hidden = true;
          if(localStorage.userType === "ROLE_APPROVER")
          {
            this.btnUnapprove.hidden = false;
          }
        }

        // var userObj = formObj.user;
        var questions = formObj.questions;

        // console.log("=========================")
        // console.log("userObj:")
        // console.log(userObj);
        // console.log("=========================")
        // console.log("questions:")
        // console.log(questions);
        // console.log("=========================")
        // console.log(this.form);


        /* TODO: questionSectionName */
        for(var i in questions)
        {
          var questionsObj = questions[i];
          // console.log(questionsObj);
          var questionType = questionsObj.questionType;
          var hasQnsResponse = false;

          if( questionsObj.questionResponse !== undefined)
          {
            hasQnsResponse = true;
          }

          var hasQnsResponseClass = "noResponse";
          if(hasQnsResponse)
          {
            hasQnsResponseClass = "hasResponse";
          }
          

          if(questionType.toLowerCase() === "text")
          {
            // console.log("text");
            var inputNameField = document.createElement('label');
            inputNameField.innerHTML = questionsObj.questionText;
            inputNameField.setAttribute('class', this.placeholderID);
            // this.form.appendChild(inputNameField);

            var inputValueField = document.createElement('input');
            inputValueField.setAttribute('type','text');
            inputValueField.setAttribute('size',20);
            if(questionsObj.questionResponse !== undefined)
            {
              for(var z in questionsObj.questionResponse)
              {
                inputValueField.setAttribute('name', questionsObj.questionResponse[z].inputName);
                inputValueField.setAttribute('value', questionsObj.questionResponse[z].inputValue);
              }
            }
            inputValueField.setAttribute('id', questionsObj.questionId);
            inputValueField.setAttribute('class', this.placeholderID + " " + hasQnsResponseClass);
            
            // this.form.appendChild(inputValueField);
            let tr = document.createElement("tr");
            let td1 = document.createElement("td");
            td1.appendChild(inputNameField)
            tr.appendChild(td1);
            
            let td2 = document.createElement("td");
            td2.setAttribute('colspan','2');
            td2.appendChild(inputValueField);
            tr.appendChild(td2);
            table.appendChild(tr);
            // this.form.appendChild(table);
          }
          else if(questionType.toLowerCase() === "radio")
          {
            let tr = document.createElement("tr");
            let td1 = document.createElement("td");

            var inputNameField2 = document.createElement('label');
            inputNameField2.innerHTML = questionsObj.questionText;
            inputNameField2.setAttribute('class', this.placeholderID);

            td1.appendChild(inputNameField2);
            tr.appendChild(td1);

            var answerChoices = questionsObj.answerChoices;
            for(var j in answerChoices)
            {
              //Creating Radio BUTTON for Answers
              var answerChoice = answerChoices[j];
              var inputValueField2 = document.createElement('input');
              inputValueField2.setAttribute('type','radio');
              inputValueField2.setAttribute('name', this.placeholderID);
              inputValueField2.setAttribute('value', answerChoice.inputValue);

              inputValueField2.setAttribute('id', questionsObj.questionId);
              inputValueField2.setAttribute('class', this.placeholderID);    

              for(var q in questionsObj.questionResponse)
              {
                var questionResponse2 = questionsObj.questionResponse[q];
                if(answerChoice !== null && questionsObj.questionResponse !== undefined)
                {                  
                  if(answerChoice.inputValue === questionResponse2.inputValue)
                  {
                    inputValueField2.setAttribute('checked', true);
                  }
                }
              } 

              // Creating Radio text
              var inputNameField3 = document.createElement('label');
              inputNameField3.innerHTML = answerChoice.inputName;
              inputNameField3.setAttribute('class', this.placeholderID + " " + hasQnsResponseClass + " .justify-content-end ");         

              let td2 = document.createElement("td");
              td2.appendChild(inputValueField2);
              td2.appendChild(inputNameField3);
              tr.appendChild(td2);

              table.appendChild(tr);
            }
          }
          else if(questionType.toLowerCase() === "checkbox")
          {
            let tr = document.createElement("tr");
            let td1 = document.createElement("td");


            // console.log("checkbox");
            var inputNameField4 = document.createElement('label');
            inputNameField4.setAttribute('class', this.placeholderID);      
            inputNameField4.innerHTML = questionsObj.questionText;

            td1.appendChild(inputNameField4);
            tr.appendChild(td1);

            this.form.innerHTML += "&emsp;&emsp;";

            var answerChoices2 = questionsObj.answerChoices;

            for(var k in answerChoices2)
            {
              //Creating Radio BUTTON for Answers
              this.form.innerHTML += "&nbsp;";
              var answerChoice2 = answerChoices2[k];
              var inputValueField4 = document.createElement('input');
              
              inputValueField4.setAttribute('id', questionsObj.questionId);
              inputValueField4.setAttribute('class', this.placeholderID);    
              inputValueField4.setAttribute('type','checkbox');
              inputValueField4.setAttribute('name', answerChoice2.inputName);
              inputValueField4.setAttribute('value', answerChoice2.inputValue);

              for(var l in questionsObj.questionResponse)
              {
                var questionResponse = questionsObj.questionResponse[l];
                if(answerChoice2 !== null && questionsObj.questionResponse !== undefined)
                {                  
                  if(answerChoice2.inputValue === questionResponse.inputValue)
                  {
                    inputValueField4.setAttribute('checked', true);
                  }
                }
              }
              let td2 = document.createElement("td");

              //Creating Radio text
              var inputNameField5 = document.createElement('label');
              inputNameField5.setAttribute('class', this.placeholderID + " " + hasQnsResponseClass);    
              inputNameField5.innerHTML = answerChoice2.inputName;
              td2.appendChild(inputValueField4);
              td2.appendChild(inputNameField5);
              tr.appendChild(td2);

              table.appendChild(tr);
            }
          }
          else
          {
            console.log("error: unknown question type for array index" + questions[23]);
            console.log(questions[i])
          }
          
          // this.form.appendChild(document.createElement('br'));
          // this.form.appendChild(document.createElement('br'));
          this.placeholderID++;
        } 
        this.form.appendChild(table)
      }

      if(this.isApprove)
      {
        console.log("APPROVED");
        this.btnSubmitForm.hidden = true;
        this.btnApprove.hidden = true;
          //disable form input fields
          let inputs = document.getElementsByTagName("input");
          for(let i = 0; i < inputs.length; i++)
          {
            inputs[i].disabled = "disabled";
          }

        if(localStorage.userType === "ROLE_APPROVER")
          {
            this.btnUnapprove.hidden = false;
          }

        this.btnGeneratePDF.hidden = false;
      }
    },
    retrieveForm()
    {
      console.log("http://localhost:8080/api/response/form_response/"+this.userId+"/"+ this.formId + "/" + localStorage.workflowId);

      axios.get("http://localhost:8080/api/response/form_response/"+this.userId+"/"+ this.formId + "/" + localStorage.workflowId, {
                    headers:{
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + localStorage.token,
                        "Access-Control-Allow-Origin": "*",
                    }
                })
                .then((response) => {
                    this.jsonData = response.data;
                    console.log(this.jsonData);

                    

                    if(this.jsonData.form !== undefined)
                    {
                      // console.log("formObj:");
                      this.formObj = this.jsonData.form;
                      if(this.jsonData.form.approvedBy !== null)
                      {
                        this.isApprove = true;
                      }
                    }
                    if(this.jsonData.form.user !== null)
                    {
                      // console.log("userObj:");
                      this.userObj = this.jsonData.form.user;
                    }
                    if(this.jsonData.form !== undefined)
                    {
                      // console.log("questions:");
                      this.qnsObj = this.jsonData.form.questions;
                      // console.log(this.qnsObj);
                    }
                    if(this.jsonData.form.userResponseId !== undefined)
                    {
                      // console.log("userResponseId:");
                      this.userResponseId = this.jsonData.form.userResponseId;
                    }

                    this.showForm();
                })        

    }
  },
  mounted() 
  {

    this.form = document.getElementById('forms');
    this.btnSubmitForm = document.getElementById('btnSubmitForm');
    this.btnGeneratePDF = document.getElementById('btnGeneratePDF');
    if(localStorage.assignVendorId != null)
    {
      this.userId = localStorage.assignVendorId;
    }
    this.retrieveForm();
    this.formStatus = document.getElementById('formStatus');
    this.btnApprove = document.getElementById('btnApproveForm');
    this.btnUnapprove = document.getElementById('btnUnapproveForm');
    if(localStorage.userType === "ROLE_APPROVER")
    {
      this.btnApprove.hidden = false;
      this.btnUnapprove.hidden = true;
    }
  },
  created()
  {
    
  },
}
</script>
