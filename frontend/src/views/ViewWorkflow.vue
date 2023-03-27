<template>
  <div class="ViewWorkflow">
    <h1>Workflow Management</h1>
    <!-- <div class="btn-group" role="currentUser">
      <button type="button" class="btn btn-secondary" @click="home">
        Home
      </button>
      <button type="button" class="btn btn-secondary" @click="logout">
        Logout -->
      <!-- </button> -->
    <!-- </div> -->
    <br /><br />
    <div v-if="userType === 'ROLE_ADMIN' || userType === 'ROLE_APPROVER'">
      <!-- <label for="role">Filter by roles:</label>
            <br>
            <select name="selectRole" id="selectRole" @change="onChange($event)">
                <option v-for="item in dropdownData" :key="item" v-bind:value="item">{{item}}</option>
            </select> -->

      <table class="table" id="showTable">
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Form</th>
            <th scope="col">Assigned Admins</th>
            <th scope="col">Assigned Vendor Staff</th>
            <th scope="col">Deadline</th>
            <th scope="col" colspan="2">Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in data" :key="item.userId">
            <td>{{ item.workflowId }}</td>
            <td>{{ item.workflowName }}</td>
            <td>{{ item.form.formName }}</td>
            <td><ul><li v-for="admin in item.assignedAdmins" :key=admin.username>{{ admin.username }}</li></ul></td>
            <td><ul><li v-for="vendor in item.assignedVendors" :key=vendor.username>{{ vendor.username }}</li></ul></td>
            <td>{{ item.deadline }}</td>
            <td v-if="item.workflowId !== this.workflowId">
              <button
                class="btn btn-warning"
                @click="editWorkflow(item.workflowId)"
              >
                Edit
              </button>
            </td>
            <td v-if="item.workflowId !== this.workflowId">
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
  name: "ViewUser",
  data() {
    return {
      data: [],
      userType: localStorage.userType,
      workflowId: JSON.parse(localStorage.data).workflowId,
      // dropdownData: ['ALL', 'ROLE_ADMIN','ROLE_APPROVER','ROLE_VENDOR'],
      // role: 'ALL',
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

    viewWorkflows() {
      axios
        .get("http://localhost:8080/api/workflow/all", {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.token,
            "Access-Control-Allow-Origin": "*",
          },
        })
        .then((response) => {
          this.data = response.data;
          this.backupData = response.data;
        });
    },
    deleteWorkflow (workflowId) {
      console.log(workflowId);
      axios
        .delete("http://localhost:8080/api/workflow/delete/" + workflowId, {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.token,
            "Access-Control-Allow-Origin": "*",
          },
        })
        .then((response) => {
          console.log(response.status);
          if (response.status == 200) {
            alert("Workflow deleted successfully");
            this.viewWorkflows();
          } else {
            alert("Err: workflow deletion failed");
          }
        });
    },
    editWorkflow (workflowId) {
        console.log(workflowId);

        axios.get("http://localhost:8080/api/workflow/" + workflowId, {
                    headers:{
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + localStorage.token,
                        "Access-Control-Allow-Origin": "*",
                    }
                })
                .then((response) => {
                    console.log(response.data);
                    localStorage.editWorkflow = JSON.stringify(response.data);
                    this.$router.push({name: 'EditWorkflow'});
                })
    }
    // onChange(event)
    // {
    //     console.log(event.target.value)
    //     this.data = this.backupData;
    //     if(event.target.value != 'ALL')
    //     {
    //         this.data = this.data.filter((item) => item.userType == event.target.value);
    //     }

    // }
  },
  created() {
    try {
      console.log("viewing workflows");
      this.viewWorkflows();
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
