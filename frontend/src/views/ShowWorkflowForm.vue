<template>
 
  <div>
    <div id="forms">
      
      
    </div>   
    <button id="btnSubmitForm" @click="submitForm()" hidden>Submit Form</button>
    <button id="btnGeneratePDF" @click="generatePDF()" hidden>Generate PDF</button>
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
        form: null,
        approved: false,
      }
  },
  methods: 
  {
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

          this.approved = formObj.approved;
          this.btnGeneratePDF.hidden = false;
          if(this.approved)
          {
            this.btnGeneratePDF.hidden = false;
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

          for(var i in questions)
          {
            var questionsObj = questions[i];
            // console.log(questionsObj);
            var questionType = questionsObj.questionType;

            if(questionType.toLowerCase() === "text")
            {
              // console.log("text");
              var inputNameField = document.createElement('label');
              inputNameField.innerHTML = questionsObj.questionText;
              inputNameField.setAttribute('class', "text-label");
              this.form.appendChild(inputNameField);

              var inputValueField = document.createElement('input');
              inputValueField.setAttribute('type','text');
              inputValueField.setAttribute('size',20);
              if(questionsObj.questionResponse !== undefined)
              {
                inputValueField.setAttribute('name', questionsObj.questionResponse.inputName);
                inputValueField.setAttribute('value', questionsObj.questionResponse.inputValue);
              }
              inputValueField.setAttribute('id', questionsObj.questionId);
              inputValueField.setAttribute('class', "text-input");
              this.form.appendChild(inputValueField);
            }
            else if(questionType.toLowerCase() === "radio")
            {
              // console.log("radio");
              //Creating Label for Questions
              var inputNameField2 = document.createElement('label');
              inputNameField2.innerHTML = questionsObj.questionText;
              inputNameField2.setAttribute('class', "radio-label");
              this.form.appendChild(inputNameField2);

              this.form.innerHTML += "&emsp;&emsp;";

              var answerChoices = questionsObj.answerChoices;
              for(var j in answerChoices)
              {
                //Creating Radio BUTTON for Answers
                this.form.innerHTML += "&nbsp;";
                var answerChoice = answerChoices[j];
                var inputValueField2 = document.createElement('input');
                inputValueField2.setAttribute('type','radio');
                inputValueField2.setAttribute('name', answerChoice.inputName);
                inputValueField2.setAttribute('value', answerChoice.inputValue);

                inputValueField2.setAttribute('class', "radio-option");                

                if(answerChoice !== null && questionsObj.questionResponse !== undefined)
                {
                  if(answerChoice.inputValue === questionsObj.questionResponse.inputValue)
                  {
                    inputValueField2.setAttribute('checked', true);
                  }
                }

                this.form.appendChild(inputValueField2);

                //Creating Radio text
                var inputNameField3 = document.createElement('label');
                inputNameField3.innerHTML = answerChoice.inputName;
                inputNameField3.setAttribute('class', "radio-option-label");                

                this.form.appendChild(inputNameField3);
              }

            }
            else if(questionType.toLowerCase() === "checkbox")
            {
              // console.log("checkbox");
              var inputNameField4 = document.createElement('label');
              
              inputNameField4.setAttribute('class', "checkbox-label");     
              inputNameField4.innerHTML = questionsObj.questionText;
              this.form.appendChild(inputNameField4);

              this.form.innerHTML += "&emsp;&emsp;";

              var answerChoices2 = questionsObj.answerChoices;

              for(var k in answerChoices2)
              {
                //Creating Radio BUTTON for Answers
                this.form.innerHTML += "&nbsp;";
                var answerChoice2 = answerChoices2[k];
                var inputValueField4 = document.createElement('input');
                
              
                inputValueField4.setAttribute('class', "checkbox-input");     
                inputValueField4.setAttribute('type','checkbox');
                inputValueField4.setAttribute('name', answerChoice2.inputName);
                inputValueField4.setAttribute('value', answerChoice2.inputValue);

                for(var l in questionsObj.questionResponse)
                {
                  var questionResponse = questionsObj.questionResponse[l];
                  if(answerChoice2.inputValue === questionResponse.inputValue)
                  {
                    inputValueField4.setAttribute('checked', true);
                  }
                }

                this.form.appendChild(inputValueField4);

                //Creating Radio text
                var inputNameField5 = document.createElement('label');
                inputNameField5.setAttribute('class', "checkbox-input-label");     
                inputNameField5.innerHTML = answerChoice2.inputName;
                this.form.appendChild(inputNameField5);
              }
            
            }
            else
            {
              console.log("error: unknown question type for array index" + i);
            }
            
            this.form.appendChild(document.createElement('br'));
            this.form.appendChild(document.createElement('br'));

          }          
        }



        // if(formObj !== null)
        // {
        //   this.btnSubmitForm.hidden = false;
        // }

        // for(var i in questionResponseInfo)
        // {
          // var questionResponseObj = questionResponseInfo[i];
          // console.log(questionResponseObj);
          // var questionType = questionResponseObj.questionType;

          // if(questionType.toLowerCase() === "text")
          // {
          //   // console.log("text");
          //   var inputNameField = document.createElement('label');
          //   inputNameField.innerHTML = questionResponseObj.questionText;
          //   inputNameField.setAttribute('class', "text-label");
          //   this.form.appendChild(inputNameField);

          //   var inputValueField = document.createElement('input');
          //   inputValueField.setAttribute('type','text');
          //   inputValueField.setAttribute('size',20);            
          //   inputValueField.setAttribute('name', questionResponseObj.questionResponse.inputName);
          //   inputValueField.setAttribute('value', questionResponseObj.questionResponse.inputValue);
          //   inputValueField.setAttribute('id', questionResponseObj.questionId);
          //   inputValueField.setAttribute('class', "text-input");
          //   this.form.appendChild(inputValueField);
          // }
          // else if(questionType.toLowerCase() === "radio")
          // {
          //   console.log("radio");
          //   //Creating Label for Questions
          //   var inputNameField2 = document.createElement('label');
          //   inputNameField2.innerHTML = questionResponseObj.questionText;
          //   this.form.appendChild(inputNameField2);

          //   this.form.innerHTML += "&emsp;&emsp;";

          //   var answerChoices = questionResponseObj.answerChoices;
          //   for(var j in answerChoices)
          //   {
          //     //Creating Radio BUTTON for Answers
          //     this.form.innerHTML += "&nbsp;";
          //     var answerChoice = answerChoices[j];
          //     var inputValueField2 = document.createElement('input');
          //     inputValueField2.setAttribute('type','radio');
          //     inputValueField2.setAttribute('name', answerChoice.inputName);
          //     inputValueField2.setAttribute('value', answerChoice.inputValue);

          //     if(answerChoice.inputValue === questionResponseObj.questionResponse.inputValue)
          //     {
          //       inputValueField2.setAttribute('checked', true);
          //     }

          //     this.form.appendChild(inputValueField2);

          //     //Creating Radio text
          //     var inputNameField3 = document.createElement('label');
          //     inputNameField3.innerHTML = answerChoice.inputName;
          //     this.form.appendChild(inputNameField3);
          //   }

          // }
          // else if(questionType.toLowerCase() === "checkbox")
          // {
          //   console.log("checkbox");
          //   var inputNameField4 = document.createElement('label');
          //   inputNameField4.innerHTML = questionResponseObj.questionText;
          //   this.form.appendChild(inputNameField4);

          //   this.form.innerHTML += "&emsp;&emsp;";

          //   var answerChoices2 = questionResponseObj.answerChoices;

          //   for(var k in answerChoices2)
          //   {
          //     //Creating Radio BUTTON for Answers
          //     this.form.innerHTML += "&nbsp;";
          //     var answerChoice2 = answerChoices2[k];
          //     var inputValueField4 = document.createElement('input');
          //     inputValueField4.setAttribute('type','checkbox');
          //     inputValueField4.setAttribute('name', answerChoice2.inputName);
          //     inputValueField4.setAttribute('value', answerChoice2.inputValue);

          //     for(var l in questionResponseObj.questionResponse)
          //     {
          //       var questionResponse = questionResponseObj.questionResponse[l];
          //       if(answerChoice2.inputValue === questionResponse.inputValue)
          //       {
          //         inputValueField4.setAttribute('checked', true);
          //       }
          //     }

          //     this.form.appendChild(inputValueField4);

          //     //Creating Radio text
          //     var inputNameField5 = document.createElement('label');
          //     inputNameField5.innerHTML = answerChoice2.inputName;
          //     this.form.appendChild(inputNameField5);
        //     }


        //   }
        //   else
        //   {
        //     console.log("error: unknown question type for array index" + i);
        //   }
          
        //   this.form.appendChild(document.createElement('br'));

        // }


    },
    retrieveForm()
    {
      axios.get("http://localhost:8080/api/response/form_response/"+this.userId+"/"+ this.formId, {
                    headers:{
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + localStorage.token,
                        "Access-Control-Allow-Origin": "*",
                    }
                })
                .then((response) => {
                    this.jsonData = response.data;
                    console.log(this.jsonData);
                    this.showForm();
                })        

    }
  },
  mounted() 
  {

    this.form = document.getElementById('forms');
    this.btnSubmitForm = document.getElementById('btnSubmitForm');
    this.btnGeneratePDF = document.getElementById('btnGeneratePDF');
    this.retrieveForm();
  },
  created()
  {
    
  },
}
</script>
