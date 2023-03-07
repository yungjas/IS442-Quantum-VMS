import "bootstrap/dist/css/bootstrap.css"
import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import App from './App.vue'
import Home from './views/Home.vue';
import About from './views/About.vue';
import router from './router'

createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: Home },
        { path: '/about', component: About }
    ]
})

const app = createApp(App)
app.use(router).mount('#app')



import "bootstrap/dist/js/bootstrap.js"