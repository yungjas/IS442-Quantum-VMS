<template>
  <div style="margin-top: 2em;">
    <h1>Assigned Workflows</h1>
    <table class="table" id="showWorkflowTable" style="margin-top: 2em;">
      <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Name</th>
          <th scope="col">Form</th>
          <th scope="col">Assigned Admins</th>
          <th scope="col">Assigned Vendor Staff</th>
          <th scope="col">Deadline</th>
          <!-- <th scope="col" colspan="2">Assigned Form</th> -->
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in assignedWorkflows" :key="item.workflowId">
          <td>{{ item.workflowId }}</td>
          <td>{{ item.workflowName }}</td>
          <td>
            <ul>
              <li v-for="form in item.forms"  :key="form.formId">
                {{ form.formName }}
                <div v-if="form.approvedBy != null">
                    <b style="color: green;">(APPROVED)</b>
                </div>
                <br>
                <button style="margin-bottom: 1em;"
                  class="btn btn-warning"
                  @click="viewWorkFlowForm(form.formId, item.workflowId)"
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
              <li v-for="vendor in item.assignedVendors" :key="vendor.username">
                {{ vendor.username }}
              </li>
            </ul>
          </td>
          <td>{{ item.deadline }}</td>
          <!-- <td v-if="item.workflowId !== this.workflowId">
            <ul class="list-unstyled">
              <li v-for="form in item.forms" :key="form.formId">
                <button style="margin-bottom: 1em;"
                  class="btn btn-warning"
                  @click="viewWorkFlowForm(form.formId, item.workflowId)"
                  >
                  View {{form.formName}} form
                </button>
              </li>
            </ul>
          </td> -->
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      data: [],
      assignedWorkflows: [],
      userType: localStorage.userType,
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
    viewWorkFlowForm(formId, workflowId)
    {
      localStorage.setItem("formId", formId);
      localStorage.setItem("workflowId", workflowId);
      this.$router.push({ name: "ShowWorkflowForm" });
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
          console.log(localStorage.userId);
          console.log(response.data);
          for (let workflow of response.data) {
            let assignedVendors = workflow.assignedVendors;
            if (assignedVendors != null) {
              for (let vendor of assignedVendors) {
                    console.log(vendor);
                if (vendor.userId === localStorage.userId) {
                    this.assignedWorkflows.push(workflow)
                }
              }
            }
          }
        });
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
