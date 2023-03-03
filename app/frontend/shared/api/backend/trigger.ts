import { backendUrl, headers } from './backend'
import axios, { toFormData } from 'axios'
import { Trigger, TriggerCompare } from '../../domain/trigger'
import { it } from 'node:test'

interface TriggerCreateRequest {
  id: string,
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
  input: string
  compare: string
  outputText?: string
  discordGuildId: string
}

interface TriggersResponse {
  triggers: TriggerResponse[]
}

const triggerResponseToDomainEntity = (item: TriggerResponse) => new Trigger({
  id: item.id,
  title: "",
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
