export const oAuthProviders = {
  discord: "discord"
}

function backendUrl(url) {
  if (!process.env.NEXT_PUBLIC_AUTH_BACKEND_BASE_URL) {
    throw new Error("The environment variable AUTH_BACKEND_BASE_URL is undefined")
  }

  let baseUrl = process.env.NEXT_PUBLIC_AUTH_BACKEND_BASE_URL

  if (typeof window === 'undefined' && process.env.AUTH_BACKEND_INTERNAL_BASE_URL) {
    baseUrl = process.env.AUTH_BACKEND_INTERNAL_BASE_URL
  }

  return `${baseUrl}${url}`
}

export function oAuthProviderAuthorize(provider) {
  fetch(backendUrl(`/api/v1/oauth/provider/${provider}/authorize`))
    .then(response => response.blob())
    .then(content => content.text())
    .then(url => window.location.href = url)
}

export async function oAuthProviderAuthorizeCode(provider, code) {
  const response = await fetch(
    backendUrl(`/api/v1/oauth/provider/${provider}/handle-authorization?code=${code}`),
    { method: 'POST' }
  )

  if (!response.ok) {
    throw new Error(`failed fetching token with response code ${response.status} and body ${await response.text()}`)
  }

  return response.text()
}

export async function fetchAuthToken(code) {
  const response = await fetch(
    backendUrl(`/oauth/token?code=${code}`),
    { method: 'POST' }
  )

  if (!response.ok) {
    throw new Error(`failed fetching token with response code ${response.status} and body ${await response.text()}`)
  }

  return await response.json()
}
