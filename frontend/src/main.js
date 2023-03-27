import "bootstrap/dist/css/bootstrap.css"
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'


import '@fortawesome/fontawesome-free/js/all'


const app = createApp(App)
app.use(router).mount('#app')

import "bootstrap/dist/js/bootstrap.js"