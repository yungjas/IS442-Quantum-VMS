<template>
  <div class="ViewForm">
    <h1>Form Management</h1>
    <div class="btn-group" role="currentUser">
      <button type="button" class="btn btn-secondary" @click="home">
        Home
      </button>
      <button type="button" class="btn btn-secondary" @click="logout">
        Logout
      </button>
      <button @click="viewForms">
        view forms
      </button>
    </div>
    <br /><br />
    <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
      <!-- <label for="role">Filter by roles:</label>
              <br>
              <select name="selectRole" id="selectRole" @change="onChange($event)">
                  <option v-for="item in dropdownData" :key="item" v-bind:value="item">{{item}}</option>
              </select> -->

      <table class="table" id="showUserTable">
        <thead>
          <tr>
            <th scope="col">FormID</th>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
            <th scope="col">Date Assigned</th>
            <th scope="col" colspan="2">Action</th>
          </tr>
        </thead>
       <tbody> 
          <tr v-for="item in data" :key="item.formId">
            <td>{{ item.formId }}</td>
            <td>{{ item.formName }}</td>
            <td>{{ item.formDescription }}</td>
            <td>{{ item.revisionDate.slice(0,10) }}</td>
            <td v-if="item.formId !== this.formId">
              <button
                class="btn btn-warning"
              >
                Edit
              </button>
            </td>
            <td v-if="item.formId !== this.formId">
              <button
                class="btn btn-danger"
                @click="deleteWorkflow(item.workflowId)"
              >
                Delete
              </button> 
          </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from "axios";

/*
    To user jquery for page length, example and sample in the following link
    https://www.freakyjolly.com/how-to-use-jquery-datatables-in-vue-js-tutorial-by-example/
*/

export default {
  name: "viewForm",
  data() {
    return {
      data: [],
      userType: localStorage.userType,
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
  },
};
</script>
