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

export function backendUrl(url: string) {
  return `${process.env.NEXT_PUBLIC_BOT_BACKEND_BASE_URL}${url}`
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

  const response = await fetch(fullUrl, requestConfig)

  if (response.status === 200) {
    try {
      return await response.json() as T
    } catch (exception) {
      console.log(exception)
    }
  }

  if (!response.ok) {
    const errorMessage = `Request to backend api ${fullUrl} respond error with body: ${await response.text()}`
    throw Error(errorMessage)
  }

  return null
}
