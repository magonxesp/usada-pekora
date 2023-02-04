import { NextApiRequest, NextApiResponse } from 'next'
import { TriggerFormData } from '../../../shared/trigger/trigger'
import { TriggerClient } from '../../../shared/trigger/client'

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  if (req.method !== 'POST') {
    res.status(405).json({})
    return
  }

  try {
    const client = new TriggerClient()
    await client.auth()
    await client.createTrigger(req.body as TriggerFormData)
    res.status(200).json({})
  } catch (exception) {
    res.status(500).json({})
  }
}
