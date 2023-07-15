import axios from 'axios'
import { backendUrl, headers } from '../shared/client/backend'
import { Guild } from './guild'

interface GuildsResponse {
  guilds: Guild[]
}

export async function fetchCurrentUserGuilds(): Promise<Guild[]> {
  try {
    const response = await axios.get<GuildsResponse>(backendUrl('/api/v1/user/me/guilds'), {
      headers: headers()
    })

    return response.data.guilds
  } catch (exception) {
    return []
  }
}
