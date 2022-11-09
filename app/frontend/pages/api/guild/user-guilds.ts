import { GuildFinder } from '../../../modules/guild/application/guild-finder'
import { DiscordApiGuildRepository } from '../../../modules/guild/infraestructure/persistence/discord-api-guild-repository'
import { NextApiRequest, NextApiResponse } from 'next'
import { serverSession } from '../../../modules/shared/infraestructure/auth/session'
import { DiscordRestClientUnauthorizedError } from '../../../modules/shared/infraestructure/discord/client-error'


export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const session = await serverSession(req, res)

  if (!session) {
    res.status(200).json([])
    return
  }

  try {
    const finder = new GuildFinder(new DiscordApiGuildRepository(session.accessToken as string))
    const guilds = await finder.userGuilds()
    res.status(200).json(guilds)
  } catch (exception) {
    if (exception instanceof DiscordRestClientUnauthorizedError) {
      res.status(403).json([])
    } else {
      res.status(500).json([])
    }
  }
}
