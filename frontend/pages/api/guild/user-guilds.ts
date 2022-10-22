import { GuildFinder } from '../../../modules/guild/application/guild-finder'
import { DiscordApiGuildRepository } from '../../../modules/guild/infraestructure/persistence/discord-api-guild-repository'
import { NextApiRequest, NextApiResponse } from 'next'
import { serverSession } from '../../../modules/shared/infraestructure/auth/session'


export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const session = await serverSession(req, res)

  if (session) {
    const finder = new GuildFinder(new DiscordApiGuildRepository(session.accessToken as string))
    const guilds = await finder.userGuilds()
    res.status(200).json(guilds)
  } else {
    res.status(200).json([])
  }
}
