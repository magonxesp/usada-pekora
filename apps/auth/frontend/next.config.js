/** @type {import('next').NextConfig} */
const nextConfig = {
  swcMinify: true,
  experimental: {
    appDir: true,
    forceSwcTransforms: true
  }
}

module.exports = nextConfig
