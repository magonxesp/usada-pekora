/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  images: {
    domains: ['cdn.discordapp.com'],
  },
  i18n: {
    locales: ['es'],
    defaultLocale: 'es'
  }
}

module.exports = nextConfig
