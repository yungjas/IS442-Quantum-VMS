<template>
    <div class="sendemail" style="margin-top: 2em;">
        <h1>Send Email</h1>
        <!-- <div class="btn-group" role="currentUser" >
            <button type="button" class="btn btn-secondary" @click="home">Home</button>
            <button type="button" class="btn btn-secondary" @click="logout">Logout</button>
        </div> -->
        <br><br>
        <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
            <div>
                <table class="table table-borderless">
                    <tbody>
                        <tr>
                            <td><label>Receiver Email Address:</label></td>
                            <td>
                                <input type="text" v-model="email" id="email" placeholder="test@gmail.com">
                            </td>
                        </tr>
                        <tr>
                            <td><label>Subject:</label></td>
                            <td>
                                <input type="text" v-model="subject" id="senderSubject">
                            </td>
                        </tr>
                        <tr>
                            <td><label>Message:</label></td>
                            <td>
                                <textarea v-model="message" id="senderMessage" rows="4" cols="50"></textarea>
                            </td>
                        </tr>                        
                        <tr>
                            <td><label>Attachment (Optional)</label></td>
                            <td>
                                <input type="file" id="input" multiple />
                            </td>
                        </tr>                        
                    </tbody>
                </table>                
                <br>
                <button type="button" class="btn btn-primary" @click="sendEmail">Send</button>
            </div>
            
        </div>
    </div>
</template>



<script>
import axios from "axios";

export default {
    name: 'sendEmail',
    data () {
        return {
            email: null,
            subject: null,
            message: null,
            data: [],
            userType: localStorage.userType,
            userId: JSON.parse(localStorage.data).userId,
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
        sendEmail()
        {
            console.log("hello");


            console.log(this.email);
            console.log(this.subject);
            console.log(this.message);

            
            if(this.email != null && this.subject != null && this.message != null)
            {
                const file = document.getElementById("input").files[0];
                console.log(file);

                if(file != undefined)
                {
                    console.log("sending email with attachment");
                    const formData = new FormData();                    
                    formData.append('to',this.email)
                    formData.append('subject',this.subject)
                    formData.append('text',this.message)
                    formData.append('file',file)

                    axios.post("http://localhost:8080/api/email/send_attachment_upload", formData, {
                                headers:{
                                    "Authorization": "Bearer " + localStorage.token,
                                    "Access-Control-Allow-Origin": "*",
                                }
                            })
                            .then((response) => 
                            {
                                console.log(response);

                                if(response.status == 200)
                                {
                                    alert("Email with attachment has been sent successfully");
                                }
                            })
                }
                else
                {
                    console.log("sending email without attachment");
                    var data = JSON.parse("{\n    \"to\": \"" + this.email + "\",\n    \"subject\": \"" + this.subject + "\",\n    \"text\": \"" + this.message + "\"\n}");
                    console.log(data);

                    axios.post("http://localhost:8080/api/email/send", data, {
                                headers:{                                    
                                    "Content-Type": "application/json",
                                    "Authorization": "Bearer " + localStorage.token,
                                    "Access-Control-Allow-Origin": "*",
                                }
                            })
                            .then((response) => 
                            {
                                console.log(response);

                                if(response.status == 200)
                                {
                                    alert("Email has been sent successfully");
                                }
                            })                    
                }

                
            }
            else
            {
                alert("Please fill in all the fields");
            }
        }
    },
    created()
    {
        try
        {
            console.log("viewing user");
            this.viewUser();
        }
        catch(e)
        {
        if(e instanceof SyntaxError)
        {
            this.$router.push({name: 'Login'});
        }
        else
        {
            console.log(e);
        }
        }
    },
}
</script>
