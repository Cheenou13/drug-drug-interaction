import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    watch: {
      // Use polling instead of native watchers
      // Useful for situations like running inside a Docker container
      usePolling: true,
    }
  },
})