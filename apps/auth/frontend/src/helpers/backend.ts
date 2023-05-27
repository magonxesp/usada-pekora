interface AccessTokenResponse {
  accessToken: string,
  expiresAt: number
}

export const oAuthProviders = {
  discord: "discord"
}

function backendUrl(url: string): string {
  if (!process.env.NEXT_PUBLIC_AUTH_BACKEND_BASE_URL) {
    throw new Error("The environment variable AUTH_BACKEND_BASE_URL is undefined")
  }

  return `${process.env.NEXT_PUBLIC_AUTH_BACKEND_BASE_URL}${url}`
}

export function oAuthProviderAuthorize(provider: string) {
  fetch(backendUrl(`/api/v1/oauth/provider/${provider}/authorize`))
    .then(response => response.blob())
    .then(content => content.text())
    .then(url => window.location.href = url)
}

export async function oAuthProviderAuthorizeCode(provider: string, code: string): Promise<string> {
  const response = await fetch(
    backendUrl(`/api/v1/oauth/provider/${provider}/handle-authorization?code=${code}`),
    { method: 'POST' }
  )

  return response.text()
}

export async function fetchAuthToken(code: string): Promise<AccessTokenResponse> {
  const response = await fetch(
    backendUrl(`/oauth/token?code=${code}`),
    { method: 'POST' }
  )

  return await response.json() as Promise<AccessTokenResponse>
}
