<template>
  <div class="login-page">
    <div class="login-card">
      <div class="login-logo">
        <div class="logo-ring">
          <div class="logo-ring-inner"></div>
          <span class="logo-sym">✦</span>
        </div>
        <h1 class="login-title">人理保障机构 CHALDEA</h1>
        <p class="login-sub">TERMINAL ACCESS</p>
      </div>

      <form class="login-form" @submit.prevent="handleLogin">
        <div class="field">
          <label>Username</label>
          <input
            v-model="username"
            type="text"
            placeholder="藤丸立香 / 玛修·基列莱特 / 达·芬奇 / 罗马尼·阿基曼"
            autocomplete="username"
          />
        </div>
        <div class="field">
          <label>Password</label>
          <input
            v-model="password"
            type="password"
            placeholder="••••••••"
            autocomplete="current-password"
          />
        </div>

        <p v-if="errorMsg" class="error-msg">{{ errorMsg }}</p>

        <button type="submit" class="login-btn" :disabled="loading">
          {{ loading ? 'AUTHENTICATING...' : 'LOGIN' }}
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const auth = useAuthStore()

const username = ref('')
const password = ref('')
const loading = ref(false)
const errorMsg = ref('')

async function handleLogin() {
  if (!username.value || !password.value) {
    errorMsg.value = 'Please enter username and password'
    return
  }
  loading.value = true
  errorMsg.value = ''

  try {
    const data = await auth.login(username.value, password.value)
    console.log('[Login] Success, token saved, navigating to /sandbox')
    const params = new URLSearchParams(window.location.search)
    const redirect = params.get('redirect') || '/sandbox'
    await router.push(redirect)
    console.log('[Login] Navigation completed')
  } catch (e) {
    console.error('[Login] Failed:', e)
    errorMsg.value = e.response?.data?.error || 'Login failed. Please try again.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgb(8, 24, 56);
  background-image:
    repeating-linear-gradient(0deg, transparent, transparent 3px, rgba(255,255,255,0.015) 3px, rgba(255,255,255,0.015) 6px),
    radial-gradient(ellipse at 20% 20%, rgba(87,150,208,0.06) 0%, transparent 60%),
    radial-gradient(ellipse at 80% 80%, rgba(0,0,0,0.2) 0%, transparent 50%);
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
}
.login-card {
  width: 380px;
  padding: 40px 36px;
  background: rgba(255, 255, 255, 0.06);
  backdrop-filter: blur(24px);
  -webkit-backdrop-filter: blur(24px);
  border: 0.5px solid rgba(255, 255, 255, 0.12);
  border-radius: 16px;
  text-align: center;
}
.login-logo { margin-bottom: 32px; }
.logo-ring {
  position: relative;
  width: 64px;
  height: 64px;
  margin: 0 auto 16px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.logo-ring-inner {
  position: absolute;
  width: 100%;
  height: 100%;
  border: 1px solid rgba(87, 150, 208, 0.3);
  border-radius: 50%;
  animation: spin 6s linear infinite;
}
.logo-sym {
  font-size: 24px;
  color: #5796D0;
  text-shadow: 0 0 12px rgba(87,150,208,0.4);
}
.login-title {
  font-size: 15px;
  font-weight: 500;
  color: rgba(255,255,255,0.9);
  letter-spacing: 1.5px;
  margin: 0 0 6px;
}
.login-sub {
  font-size: 10px;
  color: rgba(255,255,255,0.3);
  letter-spacing: 3px;
  text-transform: uppercase;
  margin: 0;
}
.login-form {
  display: flex;
  flex-direction: column;
  gap: 16px;
  text-align: left;
}
.field label {
  display: block;
  font-size: 11px;
  color: rgba(255,255,255,0.5);
  margin-bottom: 6px;
  letter-spacing: 1px;
  text-transform: uppercase;
}
.field input {
  width: 100%;
  padding: 10px 14px;
  background: rgba(255,255,255,0.06);
  border: 0.5px solid rgba(255,255,255,0.12);
  border-radius: 8px;
  color: rgba(255,255,255,0.9);
  font-size: 13px;
  outline: none;
  transition: border-color 0.2s;
  box-sizing: border-box;
}
.field input::placeholder {
  color: rgba(255,255,255,0.15);
}
.field input:focus {
  border-color: #5796D0;
}
.error-msg {
  font-size: 12px;
  color: #ff4444;
  margin: 0;
  text-align: center;
}
.login-btn {
  width: 100%;
  padding: 11px;
  background: #5796D0;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 2px;
  cursor: pointer;
  transition: all 0.2s;
}
.login-btn:hover:not(:disabled) {
  background: #4680b8;
}
.login-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
@keyframes spin {
  to { transform: rotate(360deg); }
}
</style>
