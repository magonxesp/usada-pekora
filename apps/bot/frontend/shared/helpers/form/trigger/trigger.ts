import { v4 as uuidv4 } from 'uuid'
import { createTriggerAudio, createTrigger, updateTrigger } from '../../../api/backend/trigger'
import { TriggerFormData } from './form-data'

export async function submitTriggerCreateRequest(data: TriggerFormData) {
  if (data.responseAudio != null) {
    await createTriggerAudio({
      id: uuidv4(),
      triggerId: data.id,
      guildId: data.discordGuildId,
      file: data.responseAudio
    })
  }

  await createTrigger({
    id: data.id,
    title: data.title,
    compare: data.compare,
    input: data.input,
    discordGuildId: data.discordGuildId,
    responseText: data.responseText ?? undefined
  })
}

export async function submitTriggerUpdateRequest(data: TriggerFormData) {
  // if (data.responseAudio != null) {
  //   await createTriggerAudio({
  //     id: uuidv4(),
  //     triggerId: data.uuid,
  //     guildId: data.discordServerId,
  //     file: data.responseAudio
  //   })
  // }

  await updateTrigger({
    id: data.id,
    values: {
      title: data.title,
      compare: data.compare,
      input: data.input,
      discordGuildId: data.discordGuildId,
      responseTextIdext: data.responseText ?? undefined
    }
  })
}
