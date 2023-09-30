import { request } from '../shared/api'

/**
 * Fetch user current guilds
 * 
 * @param {string} accessToken 
 * @returns {Promise}
 */
export async function fetchCurrentUserGuilds(accessToken) {
  const collection = await request('GET', '/api/v1/user/me/guilds', { accessToken })
  return collection?.guilds ?? []
}
