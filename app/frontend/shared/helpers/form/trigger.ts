import { v4 as uuidv4 } from 'uuid'
import { createTriggerAudio, createTrigger, updateTrigger } from '../../api/backend/trigger'
import { Trigger, TriggerCompare } from '../../domain/trigger'
import { Validator } from './validator'

export interface TriggerFormData {
  title: string,
  id: string,
  input: string,
  compare: string,
  outputText: string|null,
  outputAudio: File|Buffer|null,
  discordServerId: string
}

export const emptyTriggerFormData = (): TriggerFormData => ({
  title: "",
  id: uuidv4(),
  input: "",
  compare: TriggerCompare.CONTAINS,
  outputText: null,
  outputAudio: null,
  discordServerId: ""
})

export const triggerToFormData = (trigger: Trigger): TriggerFormData => ({
  title: trigger.title,
  id: trigger.id,
  input: trigger.input,
  compare: trigger.compare,
  outputText: trigger.outputText,
  outputAudio: null,
  discordServerId: trigger.discordServerId
})

export async function submitTriggerCreateRequest(data: TriggerFormData) {
  if (data.outputAudio != null) {
    await createTriggerAudio({
      id: uuidv4(),
      triggerId: data.id,
      guildId: data.discordServerId,
      file: data.outputAudio
    })
  }

  await createTrigger({
    id: data.id,
    title: data.title,
    compare: data.compare,
    input: data.input,
    discordGuildId: data.discordServerId,
    outputText: data.outputText ?? undefined
  })
}

export async function submitTriggerUpdateRequest(data: TriggerFormData) {
  // if (data.outputAudio != null) {
  //   await createTriggerAudio({
  //     id: uuidv4(),
  //     triggerId: data.uuid,
  //     guildId: data.discordServerId,
  //     file: data.outputAudio
  //   })
  // }

  await updateTrigger({
    id: data.id,
    values: {
      title: data.title,
      compare: data.compare,
      input: data.input,
      discordGuildId: data.discordServerId,
      outputText: data.outputText ?? undefined
    }
  })
}
