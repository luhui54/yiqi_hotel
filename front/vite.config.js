import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, "src"),
      '~': path.resolve(__dirname, "src/components"),
      '^': path.resolve(__dirname, "src/api")
    }
  },
  server: {
    proxy: {
      '/api': {
        target: 'https://hotel-api-devbox.onrender.com',
        changeOrigin: true,
        secure: false
      }
    }
  }
})
