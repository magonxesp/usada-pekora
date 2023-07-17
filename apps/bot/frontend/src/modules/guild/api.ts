import { request } from '../shared/api'
import { Guild, GuildCollection } from './guild'

export async function fetchCurrentUserGuilds(accessToken: string|undefined = undefined): Promise<Guild[]> {
  const collection = await request<GuildCollection>('GET', '/api/v1/user/me/guilds', { accessToken })
  return collection?.guilds ?? []
}
