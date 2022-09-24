// https://v3.nuxtjs.org/api/configuration/nuxt.config
export default defineNuxtConfig({
  nitro: {
    preset: 'node-server'
  },
  components: [
    {
      path: '~/components', // will get any components nested in let's say /components/nested
      pathPrefix: false,
    },
  ],
  build: {
    postcss: {
      postcssOptions: require('./postcss.config.js'),
    },
  },
})
