import { v4 as uuidv4 } from 'uuid'
import { createTriggerAudio, createTrigger } from '../../api/backend/trigger'
import { TriggerCompare } from '../../domain/trigger'
import { Validator } from './validator'

export interface TriggerFormData {
  title: string,
  uuid: string,
  input: string,
  compare: string,
  outputText: string|null,
  outputAudio: File|Buffer|null,
  discordServerId: string
}

export const emptyTriggerFormData = () => ({
  title: "",
  uuid: uuidv4(),
  input: "",
  compare: TriggerCompare.CONTAINS,
  outputText: null,
  outputAudio: null,
  discordServerId: ""
})

export async function submitTriggerCreateRequest(data: TriggerFormData) {
  if (data.outputAudio != null) {
    await createTriggerAudio({
      id: uuidv4(),
      triggerId: data.uuid,
      guildId: data.discordServerId,
      file: data.outputAudio
    })
  }

  await createTrigger({
    id: data.uuid,
    title: data.title,
    compare: data.compare,
    input: data.input,
    discordGuildId: data.discordServerId,
    outputText: data.outputText ?? undefined
  })
}
