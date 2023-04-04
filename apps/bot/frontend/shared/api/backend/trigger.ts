import { backendUrl, headers } from './backend'
import axios, { toFormData } from 'axios'
import { Trigger } from '../../domain/trigger'

interface TriggerCreateRequest {
  id: string,
  title: string
  input: string,
  compare: string,
  outputText?: string,
  discordGuildId: string
}

interface TriggerAudioCreateRequest {
  id: string
  file: File|Buffer
  triggerId: string
  guildId: string
}

interface TriggerResponse {
  id: string
  title: string
  input: string
  compare: string
  outputText?: string
  discordGuildId: string
}

interface TriggersResponse {
  triggers: TriggerResponse[]
}

interface TriggerUpdateRequestNewValues {
  title?: string
  input?: string
  compare?: string
  outputText?: string
  discordGuildId?: string
}

interface TriggerUpdateRequest {
  id: string
  values: TriggerUpdateRequestNewValues
}

const triggerResponseToDomainEntity = (item: TriggerResponse) => new Trigger({
  id: item.id,
  title: item.title,
  compare: item.compare,
  input: item.input,
  discordServerId: item.discordGuildId,
  outputText: item.outputText ?? null
})

export async function createTriggerAudio(audio: TriggerAudioCreateRequest) {
  await axios.post(backendUrl("/api/v1/trigger/audio"), toFormData(audio), {
    headers: headers("multipart/form-data")
  })
}

export async function createTrigger(trigger: TriggerCreateRequest) {
  await axios.post(backendUrl("/api/v1/trigger"), trigger, {
    headers: headers()
  })
}

export async function deleteTrigger(id: string) {
  await axios.delete(backendUrl(`/api/v1/trigger/${id}`), {
    headers: headers()
  })
}

export async function updateTrigger(trigger: TriggerUpdateRequest) {
  await axios.put(backendUrl(`/api/v1/trigger/${trigger.id}`), trigger.values, {
    headers: headers()
  })
}

export async function fetchTriggerById(id: string): Promise<Trigger|null> {
  try {
    const response = await axios.get<TriggerResponse>(backendUrl(`/api/v1/trigger/${id}`), {
      headers: headers()
    })

    return triggerResponseToDomainEntity(response.data)
  } catch (exception) {
    return null
  }
}

export async function fetchGuildTriggers(guildId: string): Promise<Trigger[]> {
  try {
    const response = await axios.get<TriggersResponse>(backendUrl(`/api/v1/trigger/guild/${guildId}`), {
      headers: headers()
    })

    return response.data.triggers.map(triggerResponseToDomainEntity)
  } catch (exception) {
    return []
  }
}
