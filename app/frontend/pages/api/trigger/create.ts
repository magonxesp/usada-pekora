import { NextApiRequest, NextApiResponse } from 'next'
import { triggerCreator } from '../../../shared/application-services'
import { Trigger } from '../../../shared/domain/trigger'

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  if (req.method !== 'POST') {
    res.status(405).json({})
    return
  }

  try {
    await triggerCreator().create(Trigger.fromPrimitives(JSON.parse(req.body)))
    res.status(200).json({})
  } catch (exception) {
    res.status(500).json({})
  }
}
