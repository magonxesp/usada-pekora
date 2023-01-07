import { TriggerRepository } from '../../../domain/trigger-repository'
import { Trigger, TriggerCompare, TriggerDiscordServerId, TriggerId } from '../../../domain/trigger'

export class FakeTriggerRepository implements TriggerRepository {

  async findByDiscordServerId(discordServerId: TriggerDiscordServerId): Promise<Trigger[]> {
    return [
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
  }

  async findById(uuid: TriggerId): Promise<Trigger> {
    return Trigger.fromPrimitives({
      uuid: "fee1ff15-67fc-4cb6-b464-9a567549d0fb",
      title: "Lorem fistrum está la cosa muy malar caballo",
      compare: TriggerCompare.CONTAINS,
      discordServerId: "222354445645",
      input: "chiquito",
    })
  }

  async save(trigger: Trigger): Promise<void> {

  }

  async delete(trigger: Trigger): Promise<void> {

  }

}
