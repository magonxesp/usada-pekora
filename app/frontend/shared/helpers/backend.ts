interface BackendOAuthResponse {
  access_token: string
  expires_in: number
  token_type: string
}

export abstract class BackendClient {

  protected accessToken: string = ""

  protected backendUrl(url: string) {
    return `${process.env.NEXT_BACKEND_BASE_URL}${url}`
  }

  protected headers(contentType: string = "application/json") {
    return {
      "Content-Type": contentType,
      "Accept": "application/json",
      "Authorization": `Bearer ${this.accessToken}`
    }
  }

  async auth() {
    const response = await fetch(`${process.env.NEXT_BACKEND_OAUTH_ISSUER_URI}/oauth/token`, {
      body: Object.entries({
        grant_type: "client_credentials",
        client_id: process.env.NEXT_BACKEND_OAUTH_CLIENT_ID,
        client_secret: process.env.NEXT_BACKEND_OAUTH_CLIENT_SECRET,
        audience: process.env.NEXT_BACKEND_OAUTH_AUDIENCE,
      }).map(([key, value]) => `${key}=${value}`)
        .join("&"),
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      }
    })

    const auth: BackendOAuthResponse = await response.json()
    this.accessToken = auth.access_token
  }
}
