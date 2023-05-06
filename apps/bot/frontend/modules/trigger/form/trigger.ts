import { v4 as uuidv4 } from 'uuid'
import {
  updateTrigger
} from '../update'
import { createTrigger } from '../create'
import { createTriggerTextResponse } from '../text-response/create-default'
import { createTriggerAudio } from '../audio-response/create-default'
import { TriggerFormData } from '../form'

export async function submitTriggerUpdateRequest(data: TriggerFormData) {
  // if (data.responseAudio != null) {
  //   await createTriggerAudio({
  //     id: uuidv4(),
  //     triggerId: data.uuid,
  //     guildId: data.discordServerId,
  //     file: data.responseAudio
  //   })
  // }

  // await updateTrigger({
  //   id: data.id,
  //   values: {
  //     title: data.title,
  //     compare: data.compare,
  //     input: data.input,
  //     discordGuildId: data.discordGuildId,
  //     responseTextIdext: data.responseText ?? undefined
  //   }
  // })
}
