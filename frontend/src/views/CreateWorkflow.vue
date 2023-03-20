<template>
  <div class="CreateWorkflow">
    <h1>Create Workflow</h1>
    <div class="btn-group" role="currentUser">
      <button type="button" class="btn btn-secondary" @click="home">
        Home
      </button>
      <button type="button" class="btn btn-secondary" @click="logout">
        Logout
      </button>
    </div>
    <br /><br />
    <div class="form-group">
      <label for="workflowName">Workflow Name:</label>
      <input type="text" class="form-control" id="workflowName" v-model="workflowName" />
    </div>
    <div class="form-group">
      <label for="description">Description:</label>
      <textarea class="form-control" id="description" rows="3" v-model="description"></textarea>
    </div>
    <div class="form-group">
      <label for="deadline">Deadline:</label>
      <input type="datetime-local" class="form-control" id="deadline" v-model="deadline" />
    </div>
    <div class="form-group">
      <label for="assignee">Assignee:</label>
      <input type="text" class="form-control" id="assignee" v-model="assignee" />
    </div>
    <div class="form-group">
      <label for="status">Status:</label>
      <select class="form-control" id="status" v-model="status">
        <option value="IN_PROGRESS">In Progress</option>
        <option value="COMPLETED">Completed</option>
        <option value="CANCELLED">Cancelled</option>
      </select>
    </div>
    <button type="button" class="btn btn-primary" @click="createWorkflow">Create</button>
  </div>
</template>
<script>
    import axios from "axios";

    export default {
    name: "CreateWorkflow",
    data() {
        return {
        workflowName: "",
        description: "",
        deadline: "",
        assignee: "",
        status: "",
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
        createWorkflow() {
        let data = {
            workflowName: this.workflowName,
            description: this.description,
            deadline: this.deadline,
            assignee: this.assignee,
            status: this.status,
        };

        axios
            .post("http://localhost:8080/api/workflow/create", data, {
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + localStorage.token,
                "Access-Control-Allow-Origin": "*",
            },
            })
            .then((response) => {
            if (response.status == 201) {
                alert("Workflow created successfully");
                this.home();
            } else {
                alert("Err: workflow creation failed");
            }
            })
            .catch((err) => {
            alert("Err: workflow creation failed");
            console.log(err);
            });
        },
    },
    };
</script>