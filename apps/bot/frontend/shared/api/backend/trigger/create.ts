import axios from 'axios'
import { backendUrl, headers } from '../backend'

interface TriggerCreateRequest {
  id: string
  title: string
  input: string
  compare: string
  discordGuildId: string
  responseTextId?: string
  responseAudioId?: string
  responseAudioProvider?: string
}

export async function createTrigger(trigger: TriggerCreateRequest) {
  await axios.post(backendUrl('/api/v1/trigger'), trigger, {
    headers: headers()
  })
}
