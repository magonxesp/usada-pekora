import { fetch } from 'undici'
import { Collection } from './collection'

class StrapiClientResponseError extends Error { }

export class StrapiClient {

  async request<Model>(method: string, endpoint: string, body?: string): Promise<Collection<Model>> {
    const url = `${process.env.BACKEND_BASE_URL}/api${endpoint}`
    const response = await fetch(url, {
      method,
      body,
      headers: {
        Authorization: `Bearer ${process.env.BACKEND_TOKEN}`
      }
    })

    if (!response.ok) {
      throw new StrapiClientResponseError(`An error occurred on request ${url} response with status code ${response.status} with response body: ${await response.text()}`)
    }

    return await response.json() as Collection<Model>
  }
}
