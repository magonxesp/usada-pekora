import { FileData, fileUrlToFileData } from '../../../shared/infraestructure/strapi/file'
import { Trigger, TriggerCompare } from '../../domain/trigger'

export interface TriggerAttributes {
  title: string
  uuid: string
  input: string
  compare: string
  output_text?: string
  output_audio?: FileData|null
  discord_server_id: string
}

export interface TriggerModel {
  id?: number
  attributes: TriggerAttributes
}

export function triggerAggregateToModel(trigger: Trigger): TriggerModel {
  return {
    attributes: {
      title: trigger.title,
      uuid: trigger.uuid,
      compare: trigger.compare,
      input: trigger.input,
      output_audio: fileUrlToFileData(trigger.outputAudio),
      output_text: trigger.outputText,
      discord_server_id: trigger.discordServerId
    }
  }
}

export function triggerModelToAggregate(model: TriggerModel): Trigger {
  return Trigger.fromObject({
    title: model.attributes.title,
    uuid: model.attributes.uuid,
    compare: model.attributes.compare as TriggerCompare,
    discordServerId: model.attributes.discord_server_id,
    input: model.attributes.input,
    outputAudio: model.attributes.output_audio?.data?.attributes?.url,
    outputText: model.attributes.output_text
  })
}
