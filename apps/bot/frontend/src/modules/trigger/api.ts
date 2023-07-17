import { Trigger, TriggerCollection, TriggerResponses } from './trigger'
import { request } from '../shared/api'
import { TriggerTextResponse } from './trigger-text-response'
import { TriggerDefaultResponse } from './trigger-audio-response'

export async function createTrigger(
  requestBody: {
    id: string
    title: string
    input: string
    compare: string
    discordGuildId: string
    responseTextId?: string
    responseAudioId?: string
    responseAudioProvider?: string
  },
  accessToken: string|undefined = undefined
) {
  await request<void>('POST', '/api/v1/trigger', {
    body: JSON.stringify(requestBody),
    accessToken
  })
}

export async function deleteTrigger(id: string, accessToken: string|undefined = undefined) {
  await request<void>('DELETE', `/api/v1/trigger/${id}`, { accessToken })
}

export async function updateTrigger(
  id: string,
  requestBody: {
    title?: string
    input?: string
    compare?: string
    responseTextId?: string
    responseAudioId?: string
    discordGuildId?: string
    responseAudioProvider?: string
  },
  accessToken: string|undefined = undefined
) {
  await request<void>('PUT', `/api/v1/trigger/${id}`, {
    body: JSON.stringify(requestBody),
    accessToken
  })
}

export async function fetchTriggerById(id: string, accessToken: string|undefined = undefined): Promise<Trigger | null> {
  return await request<Trigger>('GET', `/api/v1/trigger/${id}`, { accessToken })
}

export async function fetchGuildTriggers(guildId: string, accessToken: string|undefined = undefined): Promise<Trigger[]> {
  const collection = await request<TriggerCollection>('GET', `/api/v1/trigger/guild/${guildId}`, { accessToken })
  return collection?.triggers ?? []
}

export async function createTriggerTextResponse(
  requestBody: {
    id: string,
    content: string,
    type: string
  },
  accessToken: string|undefined = undefined
) {
  await request<void>('POST', '/api/v1/trigger/response/text',{
    body: JSON.stringify(requestBody),
    accessToken
  })
}

export async function deleteTriggerTextResponse(id: string, accessToken: string|undefined = undefined) {
  await request<void>('DELETE', `/api/v1/trigger/response/text/${id}`,{ accessToken })
}

export async function updateTriggerTextResponse(
  id: string,
  requestBody: {
    content?: string
    type?: string
  },
  accessToken: string|undefined = undefined
) {
  await request<void>('PUT', `/api/v1/trigger/response/text/${id}`, {
    body: JSON.stringify(requestBody),
    accessToken
  })
}

export async function fetchTriggerTextResponse(id: string, accessToken: string|undefined = undefined): Promise<TriggerTextResponse|null> {
  return await request<TriggerTextResponse>('GET', `/api/v1/trigger/response/text/${id}`, { accessToken })
}

export async function createTriggerAudio(
  requestBody: {
    id: string
    file: File
    triggerId: string
    guildId: string
  },
  accessToken: string|undefined = undefined
) {
  const formData = new FormData()
  Object.entries(requestBody).forEach(([key, value]) => formData.append(key, value))

  await request<void>('POST', '/api/v1/trigger/response/audio', {
    body: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    accessToken
  })
}

export async function deleteTriggerDefaultAudioResponse(id: string, accessToken: string|undefined = undefined) {
  await request<void>('DELETE', `/api/v1/trigger/response/audio/${id}`,{ accessToken })
}

export async function updateTriggerDefaultAudioResponse(
  id: string,
  requestBody: {
    triggerId?: string;
    guildId?: string;
    file?: File;
  },
  accessToken: string|undefined = undefined
) {
  const formData = new FormData()
  Object.entries(requestBody).forEach(([key, value]) => formData.append(key, value))

  await request<void>('PUT', `/api/v1/trigger/response/audio/${id}`, {
    body: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    accessToken
  })
}

export async function fetchTriggerDefaultAudioResponse(id: string, accessToken: string|undefined = undefined): Promise<TriggerDefaultResponse|null> {
  return await request<TriggerDefaultResponse>('GET', `/api/v1/trigger/response/audio/${id}`, { accessToken })
}

export async function fetchTriggerByIdWithResponses(id: string, accessToken: string|undefined = undefined): Promise<Trigger|null> {
  const trigger = await request<Trigger>('GET', `/api/v1/trigger/${id}`, { accessToken })

  if (!trigger) {
    return null
  }

  const responses: TriggerResponses = {}

  if (trigger.responseTextId) {
    responses.text = await fetchTriggerTextResponse(trigger.responseTextId) ?? undefined
  }

  if (trigger.responseAudioId) {
    responses.audio = await fetchTriggerDefaultAudioResponse(trigger.responseAudioId) ?? undefined
  }

  trigger.responses = responses
  return trigger
}
