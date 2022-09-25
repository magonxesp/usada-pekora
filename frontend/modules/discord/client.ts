import { fetch } from 'undici'


class RestClientResponseError extends Error { }

export class RestClient {

  private static apiVersion = 10
  private static baseUrl = `https://discord.com/api/v${RestClient.apiVersion}`

  constructor(private bearerToken: string) { }

  async request(method: string, endpoint: string, body?: string): Promise<any> {
    const response = await fetch(`${RestClient.baseUrl}${endpoint}`, {
      method,
      body,
      headers: {
        Authorization: `Bearer ${this.bearerToken}`
      }
    })

    if (!response.ok) {
      throw new RestClientResponseError(`An error occurred on request response with status code ${response.status} with response body: ${await response.text()}`)
    }

    return response.json()
  }

}
