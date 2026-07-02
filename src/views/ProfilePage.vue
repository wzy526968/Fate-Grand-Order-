<template>
  <div class="profile-page">
    <div class="scanline-overlay"></div>
    <NavBar theme="dark" />

    <div class="profile-content">
      <div class="profile-card">
        <h2 class="page-title">■ 御主档案 / MASTER PROFILE</h2>

        <div v-if="loading" class="loading-state">LOADING...</div>

        <template v-else-if="profileStore.profile">
          <div class="profile-avatar-section">
            <AvatarUpload
              :current-url="profileStore.profile.avatarUrl"
              @uploaded="onAvatarUploaded"
            />
          </div>

          <div class="profile-fields">
            <div class="pf-row">
              <span class="pf-label">USERNAME</span>
              <span class="pf-value">{{ profileStore.profile.username }}</span>
            </div>
            <div class="pf-row">
              <span class="pf-label">DISPLAY NAME</span>
              <span class="pf-value">{{ profileStore.profile.displayName || profileStore.profile.username }}</span>
            </div>
            <div class="pf-row">
              <span class="pf-label">ROLE</span>
              <span class="pf-value">ADMIN</span>
            </div>

            <div class="pf-divider"></div>

            <div class="pf-field">
              <label>BIO / 简介</label>
              <textarea v-model="editBio" rows="3" placeholder="Enter bio..."></textarea>
            </div>

            <div class="pf-field">
              <label>FAVORITE SERVANT / 契约从者</label>
              <select v-model.number="editFavoriteId">
                <option :value="null">— NONE —</option>
                <option v-for="s in servantStore.servants" :key="s.id" :value="s.id">
                  [{{ s.className }}] {{ s.name }}
                </option>
              </select>
            </div>

            <div class="pf-field">
              <label>MAGIC CIRCUIT / 魔术回路品质</label>
              <select v-model="editMagic">
                <option value="">— NOT SET —</option>
                <option value="E">E</option>
                <option value="D">D</option>
                <option value="C">C</option>
                <option value="B">B</option>
                <option value="A">A</option>
                <option value="A+">A+</option>
                <option value="A++">A++</option>
                <option value="EX">EX</option>
              </select>
            </div>

            <p v-if="saveMsg" :class="['save-msg', { error: saveError }]">{{ saveMsg }}</p>

            <button class="save-btn" @click="handleSave" :disabled="saving">
              {{ saving ? 'SAVING...' : 'SAVE CHANGES' }}
            </button>
          </div>
        </template>

        <div v-else class="loading-state">FAILED TO LOAD PROFILE.</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import NavBar from '../components/NavBar.vue'
import AvatarUpload from '../components/AvatarUpload.vue'
import { useProfileStore } from '../stores/profile'
import { useServantStore } from '../stores/servant'

const profileStore = useProfileStore()
const servantStore = useServantStore()

const loading = ref(true)
const saving = ref(false)
const saveMsg = ref('')
const saveError = ref(false)

const editBio = ref('')
const editFavoriteId = ref(null)
const editMagic = ref('')

onMounted(async () => {
  await Promise.all([
    profileStore.fetchProfile(),
    servantStore.fetchServants(),
  ])
  if (profileStore.profile) {
    editBio.value = profileStore.profile.bio || ''
    editFavoriteId.value = profileStore.profile.favoriteServantId || null
    editMagic.value = profileStore.profile.magicCircuitQuality || ''
  }
  loading.value = false
})

function onAvatarUploaded(url) {
  saveMsg.value = 'AVATAR UPLOADED.'
  saveError.value = false
  setTimeout(() => { saveMsg.value = '' }, 3000)
}

async function handleSave() {
  saving.value = true
  saveMsg.value = ''
  saveError.value = false
  try {
    await profileStore.updateProfile({
      bio: editBio.value,
      favoriteServantId: editFavoriteId.value || null,
      magicCircuitQuality: editMagic.value,
    })
    saveMsg.value = 'PROFILE SAVED.'
  } catch (e) {
    saveMsg.value = 'SAVE FAILED: ' + (e.response?.data?.error || e.message)
    saveError.value = true
  } finally {
    saving.value = false
    setTimeout(() => { saveMsg.value = '' }, 3000)
  }
}
</script>

<style scoped>
/* =============================================
   Terminal CRT aesthetic
   ============================================= */

.profile-page {
  min-height: 100vh;
  background: rgb(8, 24, 56);
  background-image:
    repeating-linear-gradient(0deg, transparent, transparent 2px, rgba(255,255,255,0.015) 2px, rgba(255,255,255,0.015) 4px),
    radial-gradient(ellipse at 20% 20%, rgba(87,150,208,0.06) 0%, transparent 60%),
    radial-gradient(ellipse at 80% 80%, rgba(0,0,0,0.2) 0%, transparent 50%);
  font-family: Arial, 'Segoe UI', sans-serif;
  color: rgb(255, 255, 255);
  position: relative;
}

/* CRT scanline overlay */
.scanline-overlay {
  position: fixed;
  inset: 0;
  z-index: 9998;
  pointer-events: none;
  background: repeating-linear-gradient(
    0deg, transparent, transparent 2px, rgba(0,0,0,0.04) 2px, rgba(0,0,0,0.04) 4px
  );
  animation: flicker 0.1s infinite;
  opacity: 0.3;
}
@keyframes flicker {
  0%   { opacity: 0.2; }
  50%  { opacity: 0.35; }
  100% { opacity: 0.2; }
}

.profile-content {
  padding-top: 72px;
  display: flex;
  justify-content: center;
}
.profile-card {
  width: 500px;
  max-width: 90vw;
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(255, 255, 255, 0.25);
  padding: 28px;
  box-shadow: 0 0 16px rgba(255,255,255,0.04);
  transition: border-color 0.25s ease, box-shadow 0.25s ease;
}
.profile-card:hover {
  border-color: rgba(255, 255, 255, 0.4);
  box-shadow: 0 0 24px rgba(255,255,255,0.06);
}
.page-title {
  font-size: 13px;
  font-weight: 500;
  color: rgb(255, 255, 255);
  letter-spacing: 2px;
  margin: 0 0 24px;
  text-align: center;
  text-shadow: 0 0 6px rgba(255,255,255,0.12);
}
.loading-state {
  text-align: center;
  color: rgba(255,255,255,0.35);
  font-size: 11px;
  padding: 40px 0;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  letter-spacing: 2px;
}
.profile-avatar-section {
  display: flex;
  justify-content: center;
  margin-bottom: 24px;
}
.profile-fields {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.pf-row {
  display: flex;
  justify-content: space-between;
  padding: 7px 0;
  border-bottom: 1px solid rgba(255,255,255,0.06);
}
.pf-label {
  font-size: 11px;
  color: rgba(255,255,255,0.5);
  letter-spacing: 1px;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
}
.pf-value {
  font-size: 12px;
  color: rgb(255,255,255);
  font-weight: 500;
}
.pf-divider {
  height: 1px;
  background: rgba(255,255,255,0.08);
  margin: 6px 0;
}
.pf-field {
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.pf-field label {
  font-size: 10px;
  color: rgba(255,255,255,0.5);
  letter-spacing: 1px;
  font-family: 'JetBrains Mono', 'SF Mono', monospace;
  margin-bottom: 2px;
}
.pf-field textarea,
.pf-field select {
  padding: 8px 10px;
  border: 1px solid rgba(255,255,255,0.15);
  font-size: 12px;
  font-family: 'Inter', sans-serif;
  color: rgb(255,255,255);
  background: rgba(0,0,0,0.25);
  outline: none;
  transition: border-color 0.2s;
}
.pf-field textarea::placeholder {
  color: rgba(255,255,255,0.2);
}
.pf-field textarea:focus,
.pf-field select:focus {
  border-color: #5796D0;
}
.pf-field select option {
  background: rgb(8, 24, 56);
  color: rgb(255,255,255);
}
.save-msg {
  font-size: 11px;
  color: #4cd964;
  margin: 4px 0;
  text-align: center;
  font-family: 'JetBrains Mono', monospace;
  letter-spacing: 1px;
}
.save-msg.error {
  color: #ff4444;
}
.save-btn {
  width: 100%;
  padding: 10px;
  background: rgb(255, 255, 255);
  color: rgb(87, 150, 208);
  border: 1px solid rgb(255, 255, 255);
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 1px;
  cursor: pointer;
  transition: all 0.2s;
  margin-top: 6px;
  font-family: Arial, sans-serif;
}
.save-btn:hover:not(:disabled) {
  background: rgb(87, 150, 208);
  color: rgb(255, 255, 255);
  border-color: rgb(255, 255, 255);
  box-shadow: 0 0 12px rgba(255,255,255,0.15);
}
.save-btn:disabled {
  opacity: 0.3;
  cursor: not-allowed;
  background: rgba(255,255,255,0.2);
  color: rgba(87,150,208,0.3);
  border-color: rgba(255,255,255,0.2);
}
</style>
