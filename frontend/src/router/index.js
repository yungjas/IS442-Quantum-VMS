import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import UpdateAccount from '../views/UpdateAccount.vue'
import ViewAccount from '../views/ViewAccount.vue'
import ViewUser from '../views/ViewUser.vue'
import EditUser from '../views/EditUser.vue'
import CreateUser from '../views/CreateUser.vue'

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
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
