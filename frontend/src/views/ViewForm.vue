<template>
  <div class="ViewForm" style="margin-top: 2em">
    <h1>Form Management</h1>
    <!-- <div class="btn-group" role="currentUser">
      <button type="button" class="btn btn-secondary" @click="home">
        Home
      </button>
      <button type="button" class="btn btn-secondary" @click="logout">
        Logout
      </button>
    </div> -->
    <!-- <br /><br /> -->
    <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
        <label for="role">Filter by template:</label>
        <br>
        <select name="selectTemplate" id="selectTemplate" @change="onChange($event)">
            <option v-for="item in dropdownData" :key="item" v-bind:value="item">{{item}}</option>
        </select>

      <table class="table" id="showUserTable">
        <thead>
          <tr>
            <th scope="col">FormID</th>
            <th scope="col">Name</th>
            <th scope="col">Last Edited</th>
            <th scope="col">Template?</th>
            <th scope="col" colspan="2">Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in data" :key="item">
            <td>{{ item.formId }}</td>
            <td>{{ item.formName }}</td>
            <td>{{ item.lastEdited }}</td>
            <td>{{ item.template  === true ? "Yes" : "No"}} </td>
            <td v-if="item.formId !== this.formId">
              <button class="btn btn-warning"
              @click="editForm(item.formId)">Edit</button>
            </td>
            <td v-if="item.formId !== this.formId">
              <button class="btn btn-danger" 
              @click="deleteForm(item.formId)">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "viewForm",
  data() {
    return {
      data: [],
      dropdownData: ['ALL', 'Yes', 'No'],
      userType: localStorage.userType,
      backupData: [],
    };
  },
  methods: {
    home: function () {
      this.$router.push({ name: "Home" });
    },
    logout: function () {
      localStorage.clear();
      this.$router.push({ name: "Login" });
    },
    viewForms() {
      axios
        .get("http://localhost:8080/api/forms/all", {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.token,
            "Access-Control-Allow-Origin": "*",
          },
        })
        .then((response) => {
          this.data = response.data;
          this.backupData = response.data;
          console.log(this.data);
        });
    },
    resetForm() {
      this.data = JSON.parse(localStorage.editWorkflow);
    },
    editForm(formId) {
      axios
        .get("http://localhost:8080/api/forms/" + formId, {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.token,
            "Access-Control-Allow-Origin": "*",
          },
        })
        .then((response) => {
          console.log(response.data);
          localStorage.editForm = JSON.stringify(response.data);
          this.$router.push({ name: "EditForm" });
        });
    },
    deleteForm(formId)
    {
      axios.delete("http://localhost:8080/api/forms/delete/" + formId, {
                    headers:{
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + localStorage.token,
                        "Access-Control-Allow-Origin": "*",
                    }
                })
                .then((response) => {
                    console.log(response.status);
                    if(response.status == 200)
                    {
                        alert("Form deleted successfully");
                        this.viewForms();
                    }
                    else
                    {
                        alert("Err: form deletion failed");
                    }
                })
    },
    onChange(event) {
      console.log(event.target.value)
      this.data = this.backupData;
      if(event.target.value != 'ALL'){
          this.data = this.data.filter((item) => item.template.toString() == event.target.value.toLowerCase());
      }            
    }
  },
  created() {
    try {
      console.log("viewing forms");
      this.viewForms();
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
