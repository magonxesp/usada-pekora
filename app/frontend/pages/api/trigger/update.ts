import { NextApiRequest, NextApiResponse } from 'next'


export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  if (req.method !== 'PATCH') {
    res.status(405).json({})
    return
  }

  try {
    //await triggerUpdater().update(Trigger.fromPrimitives(JSON.parse(req.body)))
    res.status(200).json({})
  } catch (exception) {
    res.status(500).json({})
  }
}
