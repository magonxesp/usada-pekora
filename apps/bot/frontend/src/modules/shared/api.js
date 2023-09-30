import Cookies from 'js-cookie'
import { IncomingMessage } from 'http'
import { NextApiRequestCookies } from 'next/dist/server/api-utils'

export class UnauthorizedError extends Error {}

export function backendUrl(url) {
  let baseUrl = process.env.NEXT_PUBLIC_BOT_BACKEND_BASE_URL

  if (typeof window === 'undefined' && process.env.BOT_BACKEND_INTERNAL_BASE_URL) {
    baseUrl =  process.env.BOT_BACKEND_INTERNAL_BASE_URL
  }

  return `${baseUrl}${url}`
}

/**
 * Get the default headers for the http request
 * 
 * @param {object} extraHeaders 
 * @returns {HeadersInit}
 */
export function defaultHeaders(extraHeaders = {}) {
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

/**
 * Append Authorization header with the bearer token stored in the cookie
 * 
 * @param {IncomingMessage & { cookies: NextApiRequestCookies } | null} request 
 * @returns {string|null}
 */
export function authorization(request = null) {
  return (
    (request)
      ? request.cookies[process.env.NEXT_PUBLIC_SESSION_COOKIE_NAME]
      : Cookies.get(process.env.NEXT_PUBLIC_SESSION_COOKIE_NAME)
  ) ?? null
}

/**
 * Perform a http request
 * 
 * @param {string} method 
 * @param {string} url 
 * @param {Partial<{
 *   headers: object
 *   accessToken: string
 *   body: object|string
 *   serverRequest: IncomingMessage & { 
 *      cookies: NextApiRequestCookies 
 *   }
 * }> | undefined} config
 * @returns {Promise<any|null>}
 */
export async function request(method, url, config) {
  const headers = config?.headers ?? {}
  const accessToken = authorization(config?.serverRequest) ?? config?.accessToken
  const fullUrl = backendUrl(url)

  if (accessToken) {
    headers["Authorization"] = `Bearer ${accessToken}`
  }

  /**
   * @type {RequestInit}
   */
  const requestConfig = {
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
      return await response.json()
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
