import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import UpdateAccount from '../views/UpdateAccount.vue'
import ViewAccount from '../views/ViewAccount.vue'
import ViewUser from '../views/ViewUser.vue'
import EditUser from '../views/EditUser.vue'
import CreateUser from '../views/CreateUser.vue'
import SendEmail from '../views/SendEmail.vue'

import ViewWorkflow from '../views/ViewWorkflow.vue'
import CreateWorkflow from '../views/CreateWorkflow.vue'
import EditWorkflow from '../views/EditWorkflow.vue'
import ViewForm from '../views/ViewForm.vue'
import CreateForm from '../views/CreateForm.vue'
import ShowWorkflowForm from '../views/ShowWorkflowForm.vue'


const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/updateaccount',
    name: 'UpdateAccount',
    component: UpdateAccount
  },
  {
    path: '/viewaccount',
    name: 'ViewAccount',
    component: ViewAccount
  },
  {
    path: '/viewUser',
    name: 'ViewUser',
    component: ViewUser
  },
  {
    path: '/editUser',
    name: 'EditUser',
    component: EditUser
  },
  {
    path: '/createUser',
    name: 'CreateUser',
    component: CreateUser
  },
  {
    path: '/sendEmail',
    name: 'SendEmail',
    component: SendEmail
  },
  {

    path: '/viewWorkflow',
    name: 'ViewWorkflow',
    component: ViewWorkflow
  },
  {
    path: '/createWorkflow',
    name: 'CreateWorkflow',
    component: CreateWorkflow
  },
  {
    path: '/editWorkflow',
    name: 'EditWorkflow',
    component: EditWorkflow
  },
  {
    path: '/viewForm',
    name: 'ViewForm',
    component: ViewForm
    },
  {
    path: '/createForm',
    name: 'CreateForm',
    component: CreateForm
  },
  {
    path: '/showWorkflowForm',
    name: 'ShowWorkflowForm',
    component: ShowWorkflowForm
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
