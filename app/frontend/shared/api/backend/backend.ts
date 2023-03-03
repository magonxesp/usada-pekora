import axios from 'axios'

interface BackendOAuthResponse {
  access_token: string
  expires_in: number
  token_type: string
}

let accessToken: string = ""

export function backendUrl(url: string) {
  return `${process.env.NEXT_PUBLIC_BACKEND_BASE_URL}${url}`
}

export function headers(contentType: string = "application/json") {
  return {
    "Content-Type": contentType,
    "Accept": "application/json",
    //"Authorization": `Bearer ${this.accessToken}`,
  }
}

export async function auth() {
  const bodyUrlEncoded = Object.entries({
    grant_type: "client_credentials",
    client_id: process.env.NEXT_BACKEND_OAUTH_CLIENT_ID,
    client_secret: process.env.NEXT_BACKEND_OAUTH_CLIENT_SECRET,
    audience: process.env.NEXT_BACKEND_OAUTH_AUDIENCE,
  }).map(([key, value]) => `${key}=${value}`).join("&")

  const response = await axios.post(`${process.env.NEXT_BACKEND_OAUTH_ISSUER_URI}/oauth/token`, bodyUrlEncoded, {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    }
  })

  const auth: BackendOAuthResponse = await response.data
  accessToken = auth.access_token
}
