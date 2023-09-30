import { request } from '../shared/api'

/**
 * Fetch current user
 * 
 * @param {string|undefined} accessToken
 * 
 * @returns {Promise<object|null>}
 */
export async function fetchCurrentUser(accessToken = undefined) {
  return await request('GET', `/api/v1/user/me`, { accessToken })
}
