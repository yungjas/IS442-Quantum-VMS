<template>
 
  <div>
    <div id="forms">
      
    </div>   
  </div>
  
</template>


<script>

export default {
  name: 'CreateForm',
  data () {
      return {
        jsonData: null,
        form: null,

      }
  },
  methods: 
  {
    showForm()
    {
        console.log("populating form\n=========================")
        console.log("adding answer");

        console.log(this.jsonData);

        var formObj = this.jsonData.form;
        var userObj = this.jsonData.user;
        var questionResponseInfo = this.jsonData.questionResponseInfo;

        console.log("=========================")
        console.log("formObj:")
        console.log(formObj);
        console.log("=========================")
        console.log("userObj:")
        console.log(userObj);
        console.log("=========================")
        console.log("questionResponseInfo:")
        console.log(questionResponseInfo);
        console.log("=========================")

        for(var i in questionResponseInfo)
        {
          var questionResponseObj = questionResponseInfo[i];
          console.log(questionResponseObj);
          var questionType = questionResponseObj.questionType;

          if(questionType.toLowerCase() === "text")
          {
            console.log("text");
            var inputNameField = document.createElement('label');
            inputNameField.innerHTML = questionResponseObj.questionText;
            this.form.appendChild(inputNameField);

            var inputValueField = document.createElement('input');
            inputValueField.setAttribute('type','text');
            inputValueField.setAttribute('size',20);            
            inputValueField.setAttribute('name', questionResponseObj.questionResponse.inputName);
            inputValueField.setAttribute('value', questionResponseObj.questionResponse.inputValue);
            this.form.appendChild(inputValueField);
          }
          else if(questionType.toLowerCase() === "radio")
          {
            console.log("radio");
          }
          else if(questionType.toLowerCase() === "checkbox")
          {
            console.log("checkbox");
          }
          else
          {
            console.log("error: unknown question type for array index" + i);
          }
          
          this.form.appendChild(document.createElement('br'));

        }



        // console.log(document.getElementById('forms'));

        // var inputNameField = document.createElement('input');
        // inputNameField.setAttribute('type','text');
        // inputNameField.setAttribute('name','answersArray[]');
        // inputNameField.setAttribute('siz',50);
        // inputNameField.setAttribute('placeholder','Input Name');
        // this.answerArray.appendChild(inputNameField);

        // var inputValueField = document.createElement('input');
        // inputValueField.setAttribute('type','text');
        // inputValueField.setAttribute('name','answersArray[]');
        // inputValueField.setAttribute('siz',50);
        // inputValueField.setAttribute('placeholder','Input Value');
        // this.answerArray.appendChild(inputValueField);
        
        // this.answerArray.appendChild(document.createElement('br'));
        // this.answerArray.appendChild(document.createElement('br'));
    },
  },
  mounted() 
  {
    document.onreadystatechange = () => { 
      if (document.readyState == "complete") 
      {         
          this.form = document.getElementById('forms');
          this.showForm();
      } 
  }
},
  created()
  {
    this.jsonData = {
            "form": {
                "formId": "641ffa3d49fc9d3a6e53ef4f",
                "formName": "form for company evaluation",
                "formNo": "form6",
                "revisionNo": 1,
                "lastEdited": "22/03/2023",
                "dateSubmitted": "27/03/2023"
            },
            "user": {
                "userId": "6405860aebf7433ec5f4b578",
                "email": "companyA@abcd.com",
                "username": "companyA"
            },
            "questionResponseInfo": [
                {
                    "questionId": "641ffa3d49fc9d3a6e53ef4c",
                    "answerChoices": null,
                    "questionResponse": {
                        "inputName": "",
                        "inputValue": "Selling stuff"
                    },
                    "questionType": "text",
                    "questionText": "What is your company about"
                },
                {
                    "questionId": "641ffa3d49fc9d3a6e53ef4c",
                    "answerChoices": null,
                    "questionResponse": {
                        "inputName": "companyName",
                        "inputValue": "Company A"
                    },
                    "questionType": "text",
                    "questionText": "What is your company name"
                },
                {
                    "questionId": "641ffa3d49fc9d3a6e53ef4c",
                    "answerChoices": null,
                    "questionResponse": {
                        "inputName": "companyInfo",
                        "inputValue": "We sell stuff"
                    },
                    "questionType": "text",
                    "questionText": "Give a brief info about your company"
                },                                
                {
                    "questionId": "641ffa3d49fc9d3a6e53ef4d",
                    "answerChoices": [
                        {
                            "inputName": "Poor",
                            "inputValue": "1"
                        },
                        {
                            "inputName": "Good",
                            "inputValue": "2"
                        }
                    ],
                    "questionResponse": {
                        "inputName": "Poor",
                        "inputValue": "1"
                    },
                    "questionType": "radio",
                    "questionText": "safety rating"
                }
            ]
        };
  },
}
</script>
