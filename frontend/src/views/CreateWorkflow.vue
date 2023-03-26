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
              <label for="workflowName">Deadline</label>
            </td>
            <td>
              <input type="text" id="deadline" v-model="deadline" style="width: 80%" />
            </td>
          </tr>         
          <tr>
            <td>
              <label for="forms">Select Forms:</label>
            </td>
            <td>
              <select v-model="selectedForms" id="form" style="width: 50%" multiple>
                <option v-for="form in this.allForms" :key="form.formId" :value="form">
                  {{ form.formName }}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td>
              <label for="users">Select Assigned Users:</label>
            </td>
            <td>
              <select v-model="selectedUsers" id="user" style="width: 50%" multiple>
                <option v-for="user in this.allUsers" :key="user.userId" :value="user">
                  {{ user.username }}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td>
              <label for="forms">Select Assigned Vendors:</label>
            </td>
            <td>
              <select
                v-model="selectedVendors"
                id="vendorSelect"
                style="width: 50%"
                multiple
              >
                <option
                  v-for="vendor in this.allVendors"
                  :key="vendor.userId"
                  :value="vendor"
                >
                  {{ vendor.username }}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td>
              <label for="forms">Select Assigned Admins:</label>
            </td>
            <td>
              <select
                v-model="selectedAdmins"
                id="adminSelect"
                style="width: 50%"
                multiple
              >
                <option
                  v-for="admin in this.allAdmins"
                  :key="admin.userId"
                  :value="admin"
                >
                  {{ admin.username }}
                </option>
              </select>
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
        deadline: "",
        allForms: [],
        selectedForms:{},
        allUsers:[],
        selectedUsers: [],
        allAdmins: [],
        allVendors: [],
        selectedAdmins: [],
        selectedVendors: []
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
      createWorkflow: function () {
        if (this.userType === "ROLE_ADMIN" || this.userType === "ROLE_APPROVER") {
        axios
          .post(
            "http://localhost:8080/api/workflow/create",
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
        }
      },
      // resetWorkflow: function () {
      //   this.data = {};
      // },
      addStep: function () {
        this.data.steps.push({
          name: "",
          description: "",
          assignedUsers: [],
          form: {},
        });
      },
      removeStep: function (index) {
        this.data.steps.splice(index, 1);
      },
      addForm: function () {
        this.allForms.push({
          formId: "",
          formName: "",
        });
      },
      removeForm: function (index) {
        this.allForms.splice(index, 1);
      },
      cancel: function () {
        this.$router.push({ name: "ViewWorkflow" });
      },
      validateStep: function (step) {
        if (step.name === "" || step.description === "" || step.assignedUsers.length === 0 || !step.form.formId) {
          return false;
        }
        return true;
      },
      validateWorkflow: function () {
        for (let i = 0; i < this.data.steps.length; i++) {
          if (!this.validateStep(this.data.steps[i])) {
          return false;
          }
        }
        return true;
      },
      createWorkflowSubmit: function () {
        if (this.validateWorkflow()) {
          this.createWorkflow();
        } else {
          alert("Please fill all the fields for each step and select a form.");
        }
      },
      getAllUsers() {
        axios
          .get("http://localhost:8080/api/users/all", {
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + localStorage.token,
              "Access-Control-Allow-Origin": "*",
            },
          })
          .then((response) => {
          this.allUsers = response.data;
          console.log(this.allUsers);
          for (let user of this.allUsers) {
            if (user.userType == "ROLE_ADMIN") {
              this.allAdmins.push(user);
            }
            if (user.userType == "ROLE_VENDOR") {
              this.allVendors.push(user);
            }
          }
        });
      },
      getAllForms() {
        axios
          .get("http://localhost:8080/api/forms/all", {
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + localStorage.token,
              "Access-Control-Allow-Origin": "*",
            },
          }).then((response) => {
            this.allForms = response.data
            console.log(this.allForms[0])
          })
      }
    },
    created() {
    try {
      this.getAllUsers();
      this.getAllForms();
      this.selectedUsers = this.data.assignedUsers;
      this.selectedForm = this.data.form;
      this.selectedAdmins = this.data.assignedAdmins;
      this.selectedVendors = this.data.assignedVendors;
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