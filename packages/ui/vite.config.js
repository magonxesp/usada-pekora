import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  build: {
    lib: {
      // Could also be a dictionary or array of multiple entry points
      entry: [
        './src/index.js', 
        './src/components.js',
        './src/helpers.js',
        './src/store.js',
        './src/hooks.js',
      ],
    },
    minify: false,
    sourcemap: true,
    rollupOptions: {
      external: [
        'react', 
        'react/jsx-runtime'
      ]
    }
  },
  plugins: [react()],
})
