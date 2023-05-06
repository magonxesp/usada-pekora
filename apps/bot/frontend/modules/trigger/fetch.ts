import axios from 'axios'
import { backendUrl, headers } from '../shared/client/backend'
import { fetchTriggerTextResponse } from './text-response/fetch-default'
import { Trigger, TriggerResponses } from './trigger'
import { fetchTriggerDefaultAudioResponse } from './audio-response/fetch-default'

export interface TriggerResponse {
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

export async function fetchTriggerByIdWithResponses(id: string): Promise<Trigger> {
  const response = await axios.get<TriggerResponse>(backendUrl(`/api/v1/trigger/${id}`), {
    headers: headers()
  })

  const trigger: Trigger = response.data
  const responses: TriggerResponses = {}

  if (trigger.responseTextId) {
    responses.text = await fetchTriggerTextResponse(trigger.responseTextId)
  }

  if (trigger.responseAudioId) {
    responses.audio = await fetchTriggerDefaultAudioResponse(trigger.responseAudioId)
  }

  trigger.responses = responses
  return trigger
}
