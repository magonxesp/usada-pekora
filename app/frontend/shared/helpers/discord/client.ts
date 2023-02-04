import { fetch } from 'undici'
import { DiscordRestClientResponseError, DiscordRestClientUnauthorizedError } from './client-error'


export class DiscordRestClient {

  private static apiVersion = 10
  private static baseUrl = `https://discord.com/api/v${DiscordRestClient.apiVersion}`

  constructor(private bearerToken: string) { }

  async request(method: string, endpoint: string, body?: string): Promise<any> {
    const response = await fetch(`${DiscordRestClient.baseUrl}${endpoint}`, {
      method,
      body,
      headers: {
        Authorization: `Bearer ${this.bearerToken}`
      }
    })

    if (response.status === 401) {
      throw new DiscordRestClientUnauthorizedError()
    }

    if (!response.ok) {
      throw new DiscordRestClientResponseError(`An error occurred on request response with status code ${response.status} with response body: ${await response.text()}`)
    }

    return response.json()
  }

}
