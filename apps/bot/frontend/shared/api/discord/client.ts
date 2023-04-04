import { DiscordRestClient } from './rest-client'
import { Guild } from '../../domain/guild'

export class DiscordGuildClient extends DiscordRestClient {

  private mapGuildObject(object: any): Guild {
    return {
      id: object.id,
      name: object.name,
      icon: (object.icon) ? `https://cdn.discordapp.com/icons/${object.id}/${object.icon}.png` : null,
      owner: object.owner,
      permissions: object.permissions
    }
  }

  async userGuilds(): Promise<Guild[]> {
    const guilds = await this.request('GET', '/users/@me/guilds') as any[]
    return guilds.map(this.mapGuildObject)
  }

}
