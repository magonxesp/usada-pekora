import { GuildRepository } from '../domain/guild-repository'
import { Guild } from '../domain/guild'

export class GuildFinder {

  constructor(private repository: GuildRepository) { }

  async userGuilds(): Promise<Guild[]> {
    return await this.repository.findAuthenticatedUserGuilds()
  }

}
