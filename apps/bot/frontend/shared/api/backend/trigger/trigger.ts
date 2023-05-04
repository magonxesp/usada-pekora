import axios from 'axios'
import { backendUrl, headers } from '../backend'

interface TriggerResponse {
  id: string;
  title: string;
  input: string;
  compare: string;
  responseTextId?: string;
  responseAudioId?: string;
  discordGuildId: string;
}

interface TriggersResponse {
  triggers: TriggerResponse[]
}

export type Trigger = TriggerResponse

export const triggerCompare = {
  contains: 'in',
  pattern: 'pattern'
}

export async function fetchTriggerById(id: string): Promise<Trigger | null> {
  try {
    const response = await axios.get<TriggerResponse>(backendUrl(`/api/v1/trigger/${id}`), {
      headers: headers()
    })

    return response.data
  } catch (exception) {
    return null
  }
}

export async function fetchGuildTriggers(guildId: string): Promise<Trigger[]> {
  try {
    const response = await axios.get<TriggersResponse>(backendUrl(`/api/v1/trigger/guild/${guildId}`), {
      headers: headers()
    })

    return response.data.triggers
  } catch (exception) {
    return []
  }
}
