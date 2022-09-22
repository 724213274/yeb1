import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import FriendChat from '../views/chat/FriendChat.vue'
import AdminInfo from '../views/AdminInfo.vue'

Vue.use(VueRouter)

const routes = [{
        path: '/',
        name: 'Login',
        component: Login,
        hidden: true
    },

    {
        path: '/home',
        name: 'Home',
        component: Home,
        children: [{
                path: '/chat',
                name: '在线聊天',
                component: FriendChat,
            },
            {
                path: '/userinfo',
                name: '个人中心',
                component: AdminInfo
            }
        ]
    }
]

const router = new VueRouter({
    routes
})

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch((err) => err);
};

export default router