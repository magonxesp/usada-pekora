import { backendUrl, headers } from '../shared/client/backend'
import axios from 'axios'

interface TriggerUpdateRequestNewValues {
  title?: string
  input?: string
  compare?: string
  responseTextId?: string
  responseAudioId?: string
  discordGuildId?: string
  responseAudioProvider?: string
}

interface TriggerUpdateRequest {
  id: string
  values: TriggerUpdateRequestNewValues
}

export async function updateTrigger(trigger: TriggerUpdateRequest) {
  await axios.put(backendUrl(`/api/v1/trigger/${trigger.id}`), trigger.values, {
    headers: headers()
  })
}

