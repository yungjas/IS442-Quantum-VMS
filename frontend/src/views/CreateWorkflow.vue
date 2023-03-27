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
                <select v-model="selectedForms" id="form" style="width: 50%">
                  <option v-for="form in this.displayedForms" :key="form.formId" :value="form">
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
          data: {},
          userType: localStorage.userType,
          workflowName: "",
          deadline: "",
          selectedForms:{},
          allUsers:[],
          selectedUsers: [],
          allAdmins: [],
          allVendors: [],
          selectedAdmins: [],
          selectedVendors: [],
          displayedForms: []
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
          this.deadline = "";
          this.selectedAdmins = [];
          this.selectedForms = {};
          this.selectedUsers = [];
          this.selectedVendors = [];
        },
        createWorkflow() {
          this.data.workflowName = this.workflowName;
          this.data.deadline = this.deadline;
          this.data.assignedUsers = this.selectedUsers;
          this.data.assignedVendors = this.selectedVendors;
          this.data.assignedAdmins = this.selectedAdmins;
          this.data.form = this.selectedForms;
          console.log(this.data);
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
              for (let form of response.data){
                console.log(form);
                if (form.template == true){
                  this.displayedForms.push(form);
                }
              }
              
            })
        }
      },
      created() {
      try {
        this.getAllUsers();
        this.getAllForms();
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