import { defineConfig } from 'tsup'
import cssModulesPlugin from 'esbuild-css-modules-plugin'

export default defineConfig({
  esbuildPlugins: [cssModulesPlugin()],
  entry: ['src/index.ts'],
  dts: true,
  format: ['esm', 'cjs'],
  tsconfig: './tsconfig.json',
  external: ['react'],
  loader: {
    '.png': 'dataurl',
    '.jpg': 'dataurl',
  }
})
