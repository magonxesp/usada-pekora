import { Guild } from './guild'

export interface GuildRepository {
  findAuthenticatedUserGuilds(): Promise<Guild[]>
}
