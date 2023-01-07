import { NextApiRequest, NextApiResponse } from 'next'
import { serverSession } from '../../../shared/infraestructure/auth/session'
import { DiscordRestClientUnauthorizedError } from '../../../shared/infraestructure/discord/client-error'
import { guildFinder } from '../../../shared/application-services'


export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const session = await serverSession(req, res)

  if (!session) {
    res.status(200).json([])
    return
  }

  try {
    const guilds = await guildFinder(session.accessToken as string).userGuilds()
    res.status(200).json(guilds)
  } catch (exception) {
    if (exception instanceof DiscordRestClientUnauthorizedError) {
      res.status(403).json([])
    } else {
      res.status(500).json([])
    }
  }
}
