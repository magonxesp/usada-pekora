import { v4 as uuidv4 } from 'uuid'
import {
  updateTrigger
} from '../../../api/backend/trigger/update'
import { TriggerFormData } from './form-data'
import { createTrigger } from '../../../api/backend/trigger/create'
import { createTriggerTextResponse } from '../../../api/backend/trigger/text-response/create-default'
import { createTriggerAudio } from '../../../api/backend/trigger/audio-response/create-default'

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
