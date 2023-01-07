import { NextApiRequest, NextApiResponse } from 'next'
import { triggerFinder } from '../../../shared/application-services'

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const triggers = await triggerFinder().findByDiscordServerId(req.query.id as string)
  res.status(200).json(triggers)
}
