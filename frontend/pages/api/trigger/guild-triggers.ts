import { TriggerFinder } from '../../../modules/trigger/application/trigger-finder'
import { StrapiTriggerRepository } from '../../../modules/trigger/infraestructure/persistence/strapi-trigger-repository'
import { NextApiRequest, NextApiResponse } from 'next'

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const finder = new TriggerFinder(new StrapiTriggerRepository())
  const triggers = await finder.findByDiscordServerId(req.query.id as string)
  res.status(200).json(triggers)
}
