import { request } from './api'
import { delay } from './runtime'

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

export async function fetchCurrentUserGuildsRetryingUntilHaveGuilds(accessToken) {
  let guilds = []
  let retryes = 0

  while (guilds.length == 0 && retryes < 30) {
    guilds = await fetchCurrentUserGuilds(accessToken)
    await delay(1000)
    retryes++
  }

  if (guilds.length === 0) {
    throw Error('Reached 30 tries fetching the current user guilds')
  }

  return guilds
}
