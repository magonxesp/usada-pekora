import { GuildRepository } from '../../domain/guild-repository'
import { DiscordRestClient } from '../../../shared/infraestructure/discord/client'
import { Guild } from '../../domain/guild'

export class DiscordApiGuildRepository implements GuildRepository {

  private client: DiscordRestClient

  constructor(bearerToken: string) {
    this.client = new DiscordRestClient(bearerToken)
  }

  private mapGuildObject(object: any): Guild {
    return {
      id: object.id,
      name: object.name,
      icon: (object.icon) ? `https://cdn.discordapp.com/icons/${object.id}/${object.icon}.png` : null,
      owner: object.owner,
      permissions: object.permissions
    }
  }

  async findAuthenticatedUserGuilds(): Promise<Guild[]> {
    const guilds = await this.client.request('GET', '/users/@me/guilds') as any[]
    return guilds.map(this.mapGuildObject)
  }

}
