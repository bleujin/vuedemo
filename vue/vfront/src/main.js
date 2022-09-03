import { createApp } from 'vue'
import App from './App.vue'
import { Quasar } from 'quasar'
import quasarUserOptions from './quasar-user-options'

import axios from 'axios'

// Vue.config.productionTip = false 

const app = createApp(App)
app.use(Quasar, quasarUserOptions).mount('#app')
app.config.globalProperties.$axios = axios
