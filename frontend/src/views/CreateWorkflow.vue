<template>
  <div class="CreateWorkflow">
    <h1>Create workflow</h1>
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
              <label for="workflowName">Workflow Name</label>
            </td>
            <td>
              <input type="text" id="workflowName" v-model="workflowName" style="width: 80%" />
            </td>
          </tr>         
          <tr>
            <td>
              <label for="forms">Select Form:</label>
            </td>
            <td>
              <select v-model="selectedForms" id="form" style="width: 80%" multiple>
                <option v-for="form in forms" :key="form.id" :value="form">
                  {{ form.name }}
                </option>
              </select>
              <p>You have selected: {{ selectedForms }}</p>
            </td>
          </tr>
          <tr>
            <td>
              <label for="users">Select Users:</label>
            </td>
            <td>
              <select v-model="selectedUsers" id="user" style="width: 80%" multiple>
                <option v-for="user in users" :key="user.id" :value="user">
                  {{ user.name }}
                </option>
              </select>
              <p>You have selected: {{ selectedUsers }}</p>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="btn-group" role="submitChange">
        <button type="button" class="btn btn-secondary" @click="createWorkflow">
          Create
        </button>
        <button type="button" class="btn btn-secondary" @click="resetWorkflow">
          Reset
        </button>
      </div>
    </div>
  </div>
</template>
<script>
  import axios from "axios";

  export default {
    name: "CreateWorkflow",
    data() {
      return {
        userType: localStorage.userType,
        workflowName: "",
        forms: [
        { id: 1, name: 'Form 1' },
        { id: 2, name: 'Form 2' },
        { id: 3, name: 'Form 3' },
        { id: 4, name: 'Form 4' },
        ],
        selectedForms: [],
        users:[
        {id:1, name: "Deborah"},
        {id:2, name: "Jeremy"},
        {id:3, name: "Abby"},
        {id:4, name: "Jasmine"}
        ],
        selectedUsers: []
      };
    },
    methods: {
      home() {
        this.$router.push({ name: "Home" });
      },
      logout() {
        localStorage.clear();
        this.$router.push({ name: "Login" });
      },
      resetWorkflow() {
        this.workflowName = "";
        this.form = "";
      },
      createWorkflow() {
        if (this.userType === "ROLE_ADMIN" || this.userType === "ROLE_APPROVER") {
          const data = {
            workflowName: this.workflowName,
            form: this.form,
            assignedUsers: [
              {
                userId: "641468eaa526a31d0609d563",
                userType: "ROLE_ADMIN",
                username: "jasmine88888",
                password:
                  "$2a$10$/HSxHv7n1KPCNy2N6r1Ly.YkvZwNygY1EFcuZxnf4uK4NXal6FeUq",
                email: "jasmine-lim88@hotmail.com",
              },
            ],
          };
          console.log(data);

          axios
            .post("http://localhost:8080/api/workflow/create", data, {
              headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + localStorage.token,
                "Access-Control-Allow-Origin": "*",
              },
            })
            .then((response) => {
              console.log(response);
              this.$router.push({ name: "ViewWorkflow" });
            })
            .catch((error) => {
              alert("Error:" + error.response.status);
            });
        }
      },
    },
    created() {
    try {
      console.log(" on create workflow page");
    } catch (e) {
      if (e instanceof SyntaxError) {
        this.$router.push({ name: "Login" });
      } else {
        console.log(e);
      }
    }
  },
  }
</script>