<template>
  <div class="avatar-upload">
    <div class="avatar-preview" @click="triggerInput">
      <img v-if="previewUrl" :src="previewUrl" alt="avatar" class="avatar-img" />
      <div v-else class="avatar-placeholder">
        <span>+</span>
      </div>
    </div>
    <input
      ref="fileInput"
      type="file"
      accept="image/*"
      class="file-input"
      @change="onFileSelect"
    />
    <button
      v-if="selectedFile"
      class="upload-btn"
      :disabled="uploading"
      @click="doUpload"
    >
      {{ uploading ? 'Uploading...' : 'Upload' }}
    </button>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const emit = defineEmits(['uploaded'])
const props = defineProps({
  currentUrl: { type: String, default: '' },
})

const fileInput = ref(null)
const selectedFile = ref(null)
const previewUrl = ref(props.currentUrl)
const uploading = ref(false)

function triggerInput() {
  fileInput.value?.click()
}

function onFileSelect(e) {
  const file = e.target.files?.[0]
  if (!file) return
  selectedFile.value = file
  previewUrl.value = URL.createObjectURL(file)
}

async function doUpload() {
  if (!selectedFile.value || uploading.value) return
  uploading.value = true
  try {
    const formData = new FormData()
    formData.append('file', selectedFile.value)
    const { useProfileStore } = await import('../stores/profile')
    const profile = useProfileStore()
    const result = await profile.uploadAvatar(selectedFile.value)
    emit('uploaded', result.avatarUrl)
    selectedFile.value = null
  } catch (e) {
    console.error('Upload failed', e)
  } finally {
    uploading.value = false
  }
}
</script>

<style scoped>
.avatar-upload {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}
.avatar-preview {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border: 1px solid rgba(255,255,255,0.2);
  overflow: hidden;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: border-color 0.2s;
  background: rgba(0,0,0,0.15);
}
.avatar-preview:hover {
  border-color: rgba(255,255,255,0.5);
}
.avatar-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.avatar-placeholder {
  color: rgba(255,255,255,0.3);
  font-size: 28px;
}
.file-input {
  display: none;
}
.upload-btn {
  padding: 6px 20px;
  font-size: 11px;
  border: 1px solid rgb(255, 255, 255);
  background: rgb(255, 255, 255);
  color: rgb(87, 150, 208);
  cursor: pointer;
  transition: all 0.2s;
  font-family: Arial, sans-serif;
  font-weight: 600;
  letter-spacing: 0.5px;
}
.upload-btn:disabled {
  opacity: 0.3;
  cursor: not-allowed;
  background: rgba(255,255,255,0.2);
  color: rgba(87,150,208,0.3);
  border-color: rgba(255,255,255,0.2);
}
.upload-btn:hover:not(:disabled) {
  background: rgb(87, 150, 208);
  color: rgb(255, 255, 255);
  border-color: rgb(255, 255, 255);
  box-shadow: 0 0 8px rgba(255,255,255,0.15);
}
</style>
