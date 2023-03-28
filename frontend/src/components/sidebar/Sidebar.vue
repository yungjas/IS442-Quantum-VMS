<script>
import { collapsed, toggleSidebar, sidebarWidth } from './state'
import SidebarLink from './SidebarLink'
export default {
    props: {},
    components: { SidebarLink },
    setup() {
        return { collapsed, toggleSidebar, sidebarWidth }
    },
    data(){
        return{
            user: Object,
            loggedIn: false
        }
    },
    watch: {
        $route(){
            if(localStorage.token){
                this.loggedIn = true
                // auto show side bar if logged in
                this.collapsed = false
                this.user = JSON.parse(localStorage.user)
            }
            else{
                this.loggedIn = false
                // don't show side bar if not logged in
                this.collapsed = true
            }
        }
    },
    methods: {
        logout: function() {
            localStorage.clear()
            this.$router.push("/login")
        }
    }
}
</script>

<template>
    <div class="sidebar" :style="{ width: sidebarWidth }" v-if="loggedIn">
        <h1>
            <span v-if="collapsed">
                <a href="/">
                    <img style= "width:50px;height:55px" src ="../../assets/quantum-logo.png">
                </a>
            </span>
            <a v-else href="/">
                <img style= "width:165px;height:60px" src ="../../assets/quantum-leap-incorporation-rectangular.jpg">
            </a>
        </h1>
        
        <div v-if="user.userType === 'ROLE_ADMIN' || user.userType === 'ROLE_APPROVER'">
            <SidebarLink to="./" icon="fa-solid fa-house"> Home </SidebarLink>
            <SidebarLink to="/updateAccount" icon="fa-solid fa-user"> Update Account </SidebarLink>
            <SidebarLink to="/sendEmail" icon="fas fa-envelope"> Send Email </SidebarLink>
            <SidebarLink to="/viewUser" icon="fa-solid fa-users"> View User </SidebarLink>
            <SidebarLink to="/createUser" icon="fas fa-user-plus"> Create User </SidebarLink>
            <SidebarLink to="/viewWorkflow" icon="fas fa-briefcase"> View Workflow </SidebarLink>
            <SidebarLink to="/createWorkflow" icon="fas fa-briefcase"> Create Workflow </SidebarLink>
            <SidebarLink to="/viewForm" icon="fas fa-file-invoice"> View Form </SidebarLink>
            <SidebarLink to="/createForm" icon="fas fa-file-invoice"> Create Form </SidebarLink>
            <SidebarLink to="/login" icon="fas fa-right-from-bracket" @click="logout"> Logout </SidebarLink>
        </div>

        <div v-else-if="user.userType == 'ROLE_VENDOR'">
            <SidebarLink to="./" icon="fa-solid fa-house"> Home </SidebarLink>
            <SidebarLink to="/viewAssignedWorkflows" icon="fas fa-briefcase"> View Workflow </SidebarLink>
            <SidebarLink to="/" icon="fas fa-file-invoice"> View Form </SidebarLink>
            <SidebarLink to="/login" icon="fas fa-right-from-bracket" @click="logout"> Logout </SidebarLink>
        </div>

        <span
            class="collapse-icon" 
            :class="{ 'rotate-180':collapsed }"
            @click="toggleSidebar">
            <i class = "fas fa-angle-double-left" />
        
        </span>
    </div>
</template>

<style scoped>
.sidebar {
    color: white;
    background-color: #017cc1;

    float: left;
    position: fixed;
    z-index: 1;
    top: 0;
    left: 0;
    bottom: 0;
    padding: 0.5em;

    transition: 0.3s ease;

    display: flex;
    flex-direction: column;
}

.collapse-icon {
    position: absolute;
    bottom: 0;
    padding: 0.75em;

    color:white;

    transition: 0.2s linear;
}

.rotate-180 {
    transform: rotate(180deg);
    transition: 0.2s linear;
}
</style>