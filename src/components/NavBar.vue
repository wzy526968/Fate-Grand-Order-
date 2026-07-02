<template>
  <nav :class="['navbar', theme]">
    <div class="nav-left">
      <router-link to="/" class="nav-logo">
        <span class="logo-icon">✦</span>
        <span class="logo-text">CHALDEA</span>
      </router-link>
    </div>
    <div class="nav-center">
      <router-link to="/" class="nav-link">Home</router-link>
      <router-link to="/portal" class="nav-link">Observation</router-link>
      <router-link to="/facilities" class="nav-link">Facility</router-link>
      <router-link to="/sandbox" class="nav-link">Servant</router-link>
      <router-link to="/staff" class="nav-link">Personnel</router-link>
      <router-link to="/profile" class="nav-link">Profile</router-link>
    </div>
    <div class="nav-right">
      <span class="nav-user">{{ authStore.user?.displayName || authStore.user?.username }}</span>
      <button class="nav-logout" @click="handleLogout">Logout</button>
    </div>
  </nav>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

defineProps({
  theme: { type: String, default: 'light' },
})

const router = useRouter()
const authStore = useAuthStore()

function handleLogout() {
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 28px;
  height: 52px;
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
}

/* Light variant */
.navbar.light {
  background: rgba(255, 255, 255, 0.85);
  border-bottom: 0.5px solid rgba(100, 180, 255, 0.2);
}
.navbar.light .logo-text { color: #1d1d1f; }
.navbar.light .nav-link { color: #86868b; }
.navbar.light .nav-link:hover,
.navbar.light .nav-link.router-link-active { color: #1d1d1f; }
.navbar.light .nav-user { color: #86868b; }
.navbar.light .nav-logout {
  border-color: rgba(100, 180, 255, 0.3);
  color: #86868b;
}
.navbar.light .nav-logout:hover {
  background: rgba(100, 180, 255, 0.06);
  color: #1d1d1f;
}

/* Dark variant */
.navbar.dark {
  background: rgba(0, 0, 0, 0.4);
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}
.navbar.dark .logo-text { color: rgba(255,255,255,0.9); }
.navbar.dark .nav-link { color: rgba(255,255,255,0.5); }
.navbar.dark .nav-link:hover,
.navbar.dark .nav-link.router-link-active { color: rgb(255,255,255); }
.navbar.dark .nav-user { color: rgba(255,255,255,0.5); }
.navbar.dark .nav-logout {
  border-color: rgba(255,255,255,0.2);
  color: rgba(255,255,255,0.6);
}
.navbar.dark .nav-logout:hover {
  background: rgba(255,255,255,0.08);
  color: rgb(255,255,255);
}

/* Shared */
.nav-left { display: flex; align-items: center; }
.nav-logo {
  display: flex;
  align-items: center;
  gap: 8px;
  text-decoration: none;
}
.logo-icon { font-size: 16px; color: #5796D0; }
.logo-text {
  font-size: 14px;
  font-weight: 600;
  letter-spacing: 1.5px;
}
.nav-center { display: flex; gap: 24px; }
.nav-link {
  font-size: 13px;
  text-decoration: none;
  transition: color 0.2s;
  font-weight: 400;
}
.nav-right {
  display: flex;
  align-items: center;
  gap: 14px;
}
.nav-user {
  font-size: 12px;
}
.nav-logout {
  padding: 4px 12px;
  font-size: 11px;
  border-radius: 6px;
  background: transparent;
  cursor: pointer;
  transition: all 0.2s;
  font-family: inherit;
}
</style>
