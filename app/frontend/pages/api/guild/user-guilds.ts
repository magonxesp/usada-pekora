import { NextApiRequest, NextApiResponse } from 'next'
import { serverSession } from '../../../shared/helpers/session'
import { DiscordRestClientUnauthorizedError } from '../../../shared/api/discord/client-error'
import { DiscordGuildClient } from '../../../shared/api/discord/client'


export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const session: any = await serverSession(req, res)
  const token: string = session.accessToken ?? ''

  if (!session) {
    res.status(200).json([])
    return
  }

  try {
    const client = new DiscordGuildClient(token)
    const guilds = await client.userGuilds()
    res.status(200).json(guilds)
  } catch (exception) {
    if (exception instanceof DiscordRestClientUnauthorizedError) {
      res.status(403).json([])
    } else {
      res.status(500).json([])
    }
  }
}
