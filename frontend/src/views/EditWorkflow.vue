<template>
  <div class="EditWorkflow">
    <h1>Edit workflow</h1>
    <div class="btn-group" role="currentUser">
      <button type="button" class="btn btn-secondary" @click="home">
        Home
      </button>
      <button type="button" class="btn btn-secondary" @click="logout">
        Logout
      </button>
    </div>
    <br /><br />
    <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
      <table class="table">
        <tbody>
          <tr>
            <td>
              <label>Workflow ID</label>
            </td>
            <td>
              <input type="text" v-model="this.data.workflowId" />
            </td>
          </tr>
          <tr>
            <td>
              <label>Workflow Name</label>
            </td>
            <td>
              <input type="text" v-model="this.data.workflowName" />
            </td>
          </tr>
          <tr>
            <td>
              <label>Deadline</label>
            </td>
            <td>
              <input type="text" v-model="this.data.deadline" />
            </td>
          </tr>
          <tr>
            <td>
              <label for="forms">Select Users:</label>
            </td>
            <td>
              <select
                v-model="selectedUsers"
                id="userSelect"
                style="width: 50%"
                multiple
              >
                <option v-for="user in this.allUsers" :key="user.userId" :value="user" >
                  {{ user.username }}
                </option>
              </select>
              <p>You have selected: {{ selectedUsers }}</p>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="btn-group" role="submitChange">
        <button type="button" class="btn btn-secondary" @click="updateWorkflow">
          Update
        </button>
        <button type="button" class="btn btn-secondary" @click="resetWorkflow">
          Reset
        </button>
      </div>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src

import axios from "axios";

//import HelloWorld from '@/components/HelloWorld.vue'
export default {
  name: "EditWorkflow",
  data() {
    return {
      data: JSON.parse(localStorage.editWorkflow),
      userType: localStorage.userType,
      allUsers: [],
      selectedUsers: [],
      workflowId: "",
      workflowName: "",
      deadline: "",
      form: "",
    };
  },
  methods: {
    home: function () {
      localStorage.removeItem("editWorkflow");
      this.$router.push({ name: "Home" });
    },
    logout: function () {
      localStorage.clear();
      this.$router.push({ name: "Login" });
    },
    update: function () {
      if (this.userType === "ROLE_ADMIN" || this.userType === "ROLE_APPROVER") {
        this.updateAccount();
      }
    },
    resetWorkflow: function () {
      this.data = JSON.parse(localStorage.editWorkflow);
    },
    updateWorkflow() {
      console.log(this.data);
      this.data.assignedUsers = this.selectedUsers
      axios
        .put(
          "http://localhost:8080/api/workflow/update/" + this.data.workflowId,
          this.data,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + localStorage.token,
              "Access-Control-Allow-Origin": "*",
            },
          }
        )
        .then((response) => {
          console.log(response);
          this.$router.push({ name: "ViewWorkflow" });
        })
        .catch(function (error) {
          alert("Error:" + error.response.status);
        });
    },
    getAllUsers() {
      axios.get("http://localhost:8080/api/users/all", {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.token,
          "Access-Control-Allow-Origin": "*",
        },
      }).then((response)=> {
        this.allUsers = response.data
        console.log(this.allUsers);
      })
    },
  },

  created() {
    try {
      this.getAllUsers()
      this.selectedUsers = this.data.assignedUsers
      console.log(" on edit workflow page");
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
