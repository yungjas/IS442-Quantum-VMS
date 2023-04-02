<template>
  <div class="ViewWorkflow" style="margin-top: 2em">
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
      <label for="role">Sort by deadline:</label>
      <br />
      <select
        name="selectTemplate"
        id="selectTemplate"
        @change="onChange($event)"
      >
        <option v-for="item in dropdownData" :key="item" v-bind:value="item">
          {{ item }}
        </option>
      </select>

      <table class="table" id="showTable">
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Form</th>
            <th scope="col">Assigned Admins</th>
            <th scope="col">Assigned Vendor Staff</th>
            <th scope="col">Deadline</th>
            <th scope="col" colspan="3">Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in data" :key="item.userId">
            <td>{{ item.workflowId }}</td>
            <td>{{ item.workflowName }}</td>
            <td>
              <ul>
                <li v-for="form in item.forms"  :key="form.formId">
                  {{ form.formName }}
                  <div v-if="form.approvedBy != null">
                    <b style="color: green;">(APPROVED)</b>
                  </div>
                  <div else>
                    <b> {{form.status}} </b>
                  </div>
                  <br>
                  <button
                    class="btn btn-warning"
                    style="margin-bottom: 1em;"
                    @click="
                    viewWorkFlowForm(
                      form.formId,
                      item.assignedVendors,
                      item.workflowId
                    )
                  "
                  >
                View form
                </button>
                </li>
              </ul>
            </td>
            <td>
              <ul>
                <li v-for="admin in item.assignedAdmins" :key="admin.username">
                  {{ admin.username }}
                </li>
              </ul>
            </td>
            <td>
              <ul>
                <li
                  v-for="vendor in item.assignedVendors"
                  :key="vendor.username"
                >
                  {{ vendor.username }}
                </li>
              </ul>
            </td>
            <td>{{ item.deadline }}</td>
            <td v-if="item.workflowId !== this.workflowId">
              <span v-for="form in item.forms"  :key="form.formId">
                
              <!-- <button
                class="btn btn-warning"
                style="margin-bottom: 1em;"
                @click="
                  viewWorkFlowForm(
                    form.formId,
                    item.assignedVendors,
                    item.workflowId
                  )
                "
              >
                View {{form.formName}} form
              </button> -->
            </span>
            </td>
            <td v-if="item.workflowId !== this.workflowId">
              <button
                class="btn btn-primary"
                @click="editWorkflow(item.workflowId)"
              >
                Edit Workflow
              </button>
            </td>
            <td v-if="item.workflowId !== this.workflowId">
              <button
                class="btn btn-danger"
                @click="deleteWorkflow(item.workflowId)"
              >
                Delete Workflow
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
      dropdownData: ["None", "Latest", "Earliest"],
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
    viewWorkFlowForm(formId, assignedVendors, workflowId) {
      console.log(assignedVendors);
      localStorage.setItem("formId", formId);
      localStorage.setItem("workflowId", workflowId);
      if (assignedVendors.length > 0) {
        localStorage.setItem("assignVendorId", assignedVendors[0].userId);
        this.$router.push({ name: "ShowWorkflowForm" });
      } else {
        alert("No user assigned to this workflow yet");
      }
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
          console.log(this.data);

        });
    },
    deleteWorkflow(workflowId) {
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
    editWorkflow(workflowId) {
      console.log(workflowId);

      axios
        .get("http://localhost:8080/api/workflow/" + workflowId, {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.token,
            "Access-Control-Allow-Origin": "*",
          },
        })
        .then((response) => {
          console.log(response.data);
          localStorage.editWorkflow = JSON.stringify(response.data);
          this.$router.push({ name: "EditWorkflow" });
        });
    },
    onChange(event) {
      this.data = this.backupData.slice()
      if (event.target.value == "Latest") {
        this.data = this.data.sort((a, b) => {
          var aa = a.deadline.split("/").reverse().join();
          var bb = b.deadline.split("/").reverse().join();
          return aa > bb ? -1 : aa < bb ? 1 : 0;
        });
      }
      if (event.target.value == "Earliest") {
        this.data = this.data.sort((a, b) => {
          var aa = a.deadline.split("/").reverse().join();
          var bb = b.deadline.split("/").reverse().join();
          return aa < bb ? -1 : aa > bb ? 1 : 0;
        });
      }
    },
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
