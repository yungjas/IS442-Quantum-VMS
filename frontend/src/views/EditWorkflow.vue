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
          <tr v-for="(v, k) in data" :key="k.workflowId">
            <td
              v-if="
                k !== 'token' &&
                k !== 'tokenType' &&
                k !== 'userId' &&
                k !== 'password'
              "
            >
              <label>{{ k }}</label>
            </td>
            <td
              v-if="
                k !== 'token' &&
                k !== 'tokenType' &&
                k !== 'userId' &&
                k !== 'password'
              "
            >
              <input
                type="text"
                v-bind:id="k"
                v-bind:value="v"
                style="width: 100%"
              />
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
      workflowId: "",
      workflowName: "",
      form: ""

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
      console.log(localStorage.token);
      let data = "{";
      for (var v in this.data) {
        console.log(document.getElementById(v).value);
        data += '"' + v + '":"' + document.getElementById(v).value + '",';
      }

      data = data.substring(0, data.length - 1);
      data += "}";

      data = JSON.parse(data);
      // temp to remove assinged users
      // data.assignedUsers = null;
      data.form = null;
      // hardcode for now
      data.assignedUsers = [
        {
          userId: "641468eaa526a31d0609d563",
          userType: "ROLE_ADMIN",
          username: "jasmine88888",
          password:
            "$2a$10$/HSxHv7n1KPCNy2N6r1Ly.YkvZwNygY1EFcuZxnf4uK4NXal6FeUq",
          email: "jasmine-lim88@hotmail.com",
        },
      ];
      console.log(data);

      axios
        .put(
          "http://localhost:8080/api/workflow/update/" + this.data.workflowId,
          data,
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
  },

  created() {
    try {
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
