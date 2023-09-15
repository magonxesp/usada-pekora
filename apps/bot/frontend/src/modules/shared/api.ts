import Cookies from 'js-cookie'
import { IncomingMessage } from 'http'
import { NextApiRequestCookies } from 'next/dist/server/api-utils'

export type Headers = {
  [key: string]: string|undefined
}

export type RequestConfig = {
  headers: Headers
  accessToken: string
  body: FormData|string
  serverRequest: NextServerRequest
}

type NextServerRequest = IncomingMessage & { cookies: NextApiRequestCookies }
export class UnauthorizedError extends Error {}

export function backendUrl(url: string) {
  let baseUrl = process.env.NEXT_PUBLIC_BOT_BACKEND_BASE_URL

  if (typeof window === 'undefined' && process.env.BOT_BACKEND_INTERNAL_BASE_URL) {
    baseUrl =  process.env.BOT_BACKEND_INTERNAL_BASE_URL
  }

  return `${baseUrl}${url}`
}

export function defaultHeaders(extraHeaders: Headers = {}): HeadersInit {
  const headers = {
    "Content-Type": "application/json",
    "Accept": "application/json",
    ...extraHeaders
  }

  return Object.fromEntries(
    Object.entries(headers)
      .filter(([_, value]) => typeof value !== 'undefined')
  )
}

export function authorization(request: NextServerRequest|null = null): string|null {
  return (
    (request)
      ? request.cookies[process.env.NEXT_PUBLIC_SESSION_COOKIE_NAME as string]
      : Cookies.get(process.env.NEXT_PUBLIC_SESSION_COOKIE_NAME as string)
  ) ?? null
}

export async function request<T>(method: string, url: string, config?: Partial<RequestConfig>): Promise<T|null> {
  const headers = config?.headers ?? {}
  const accessToken = authorization(config?.serverRequest) ?? config?.accessToken
  const fullUrl = backendUrl(url)

  if (accessToken) {
    headers["Authorization"] = `Bearer ${accessToken}`
  }

  const requestConfig: RequestInit = {
    method,
    body: config?.body,
    headers: defaultHeaders(headers),
    cache: "no-cache",
    credentials: "include"
  }

  console.log(`Attempting to fetch url ${fullUrl}`)
  const response = await fetch(fullUrl, requestConfig)

  if (response.status === 200) {
    try {
      return await response.json() as T
    } catch (exception) {
      console.warn(exception)
    }
  }

  if (response.status === 401) {
    console.warn(`Request to backend api ${fullUrl} respond with Unauthorized status`)
    return null
  }

  if (!response.ok) {
    const errorMessage = `Request to backend api ${fullUrl} respond error with status code ${response.status} and body: ${await response.text()}`
    throw Error(errorMessage)
  }

  return null
}
