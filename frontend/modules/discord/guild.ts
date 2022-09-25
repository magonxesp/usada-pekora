import { RestClient } from './client'

export interface Guild {
  id: number
  name: string
  icon: string|null
  owner: string
  permissions: string
}

const mapGuildObject = (object: any): Guild => ({
  id: object.id,
  name: object.name,
  icon: (object.icon) ? `https://cdn.discordapp.com/icons/${object.id}/${object.icon}.png` : null,
  owner: object.owner,
  permissions: object.permissions
})

export async function userGuilds(bearerToken: string): Promise<Guild[]> {
  const client = new RestClient(bearerToken)
  const guilds = await client.request('GET', '/users/@me/guilds') as any[]

  return guilds.map(mapGuildObject)
}
