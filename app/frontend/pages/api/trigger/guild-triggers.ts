import { NextApiRequest, NextApiResponse } from 'next'
import { Trigger, TriggerCompare } from '../../../shared/domain/trigger'

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  //const finder = new TriggerFinder(new StrapiTriggerRepository())
  //const triggers = await finder.findByDiscordServerId(req.query.id as string)
  const triggers: Trigger[] = [
    Trigger.fromPrimitives({
      uuid: "fee1ff15-67fc-4cb6-b464-9a567549d0fb",
      title: "Lorem fistrum está la cosa muy malar caballo",
      compare: TriggerCompare.CONTAINS,
      discordServerId: "222354445645",
      input: "chiquito",
    }),
    Trigger.fromPrimitives({
      uuid: "4af2bd18-6b33-4000-bddc-bf8ae7662c51",
      title: "Lorem fistrum está la cosa muy malar caballo",
      compare: TriggerCompare.PATTERN,
      discordServerId: "222354445645",
      input: "chiquito",
      outputText: "Lorem fistrum está la cosa muy malar caballo"
    }),
    Trigger.fromPrimitives({
      uuid: "d360c313-a5d6-440b-8274-b48a3c98065e",
      title: "Lorem fistrum está la cosa muy malar caballo",
      compare: TriggerCompare.CONTAINS,
      discordServerId: "222354445645",
      input: "chiquito",
      outputText: "Lorem fistrum está la cosa muy malar caballo",
      outputAudio: "https://example.com"
    }),
    Trigger.fromPrimitives({
      uuid: "8df821bc-e77b-4573-87ce-a163494a53d1",
      title: "Lorem fistrum está la cosa muy malar caballo",
      compare: TriggerCompare.CONTAINS,
      discordServerId: "222354445645",
      input: "chiquito",
      outputAudio: "https://example.com"
    }),
  ]
  res.status(200).json(triggers)
}
