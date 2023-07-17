export type Guild = {
  id: string
  name: string
  iconUrl: string
  providerId: string
  provider: string
}

export type GuildCollection = {
  guilds: Guild[]
}
