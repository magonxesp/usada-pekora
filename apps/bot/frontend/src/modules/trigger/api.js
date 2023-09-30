import { request } from '../shared/api'

/**
 * Create trigger
 * 
 * @param {{
 *   id: string
 *   title: string
 *   input: string
 *   compare: string
 *   guildId: string
 *   responseTextId?: string
 *   responseAudioId?: string
 * }} requestBody
 * @param {string|undefined} accessToken
 *
 * @returns {Promise<void>}
 */
export async function createTrigger(requestBody, accessToken = undefined) {
  await request('POST', '/api/v1/trigger', {
    body: JSON.stringify(requestBody),
    accessToken
  })
}

/**
 * Delete trigger
 * 
 * @param {string} id 
 * @param {string|undefined} accessToken
 * 
 * @returns {Promise<void>}
 */
export async function deleteTrigger(id, accessToken = undefined) {
  await request('DELETE', `/api/v1/trigger/${id}`, { accessToken })
}

/**
 * Update trigger
 * 
 * @param {string} id 
 * @param {{
 *   title?: string
 *   input?: string
 *   compare?: string
 *   responseTextId?: string
 *   responseAudioId?: string
 *   guildId?: string
 * }} requestBody
 * @param {string|undefined} accessToken
 * 
 * @returns {Promise<void>}
 */
export async function updateTrigger(
  id,
  requestBody,
  accessToken = undefined
) {
  await request('PUT', `/api/v1/trigger/${id}`, {
    body: JSON.stringify(requestBody),
    accessToken
  })
}

/**
 * Get trigger by id
 * 
 * @param {string} id 
 * @param {string|undefined} accessToken 
 * @returns {Promise<object | null>}
 */
export async function fetchTriggerById(id, accessToken = undefined) {
  return await request('GET', `/api/v1/trigger/${id}`, { accessToken })
}

/**
 * Get triggers by guild
 * 
 * @param {string} guildId 
 * @param {string|undefined} accessToken
 * 
 * @returns {Promise<object[]>}
 */
export async function fetchGuildTriggers(guildId, accessToken = undefined) {
  const collection = await request('GET', `/api/v1/trigger/guild/${guildId}`, { accessToken })
  return collection?.triggers ?? []
}

/**
 * Create trigger text response
 * 
 * @param {{
 *   id: string,
 *   content: string,
 *   type: string
 * }} requestBody
 * @param {string|undefined} accessToken 
 * 
 * @returns {Promise<void>}
 */
export async function createTriggerTextResponse(requestBody, accessToken = undefined) {
  await request('POST', '/api/v1/trigger/response/text',{
    body: JSON.stringify(requestBody),
    accessToken
  })
}

/**
 * Delete trigger text response
 * 
 * @param {string} id 
 * @param {string|undefined} accessToken 
 * 
 * @returns {Promise<void>}
 */
export async function deleteTriggerTextResponse(id, accessToken = undefined) {
  await request('DELETE', `/api/v1/trigger/response/text/${id}`,{ accessToken })
}

/**
 * Update trigger text response
 * 
 * @param {string} id 
 * @param {{
 *   content?: string
 *   type?: string
 * }} requestBody 
 * @param {string|undefined} accessToken
 * 
 * @returns {Promise<void>}
 */
export async function updateTriggerTextResponse(id, requestBody, accessToken = undefined) {
  await request('PUT', `/api/v1/trigger/response/text/${id}`, {
    body: JSON.stringify(requestBody),
    accessToken
  })
}

/**
 * Get trigger text response by id
 * 
 * @param {string} id 
 * @param {string|undefined} accessToken
 * 
 * @returns {Promise<object|null>}
 */
export async function fetchTriggerTextResponse(id , accessToken = undefined) {
  return await request('GET', `/api/v1/trigger/response/text/${id}`, { accessToken })
}

/**
 * Create trigger audio response
 * 
 * @param {{
 *   id: string
 *   file: File
 *   triggerId: string
 *   guildId: string
 * }} requestBody 
 * @param {string|undefined} accessToken
 * 
 * @returns {Promise<void>}
 */
export async function createTriggerAudio(requestBody, accessToken = undefined) {
  const formData = new FormData()
  Object.entries(requestBody).forEach(([key, value]) => formData.append(key, value))

  await request('POST', '/api/v1/trigger/response/audio', {
    body: formData,
    headers: {
      'Content-Type': undefined
    },
    accessToken
  })
}

/**
 * Delete trigger audio response
 * 
 * @param {string} id 
 * @param {string|undefined} accessToken
 * 
 * @returns {Promise<void>}
 */
export async function deleteTriggerDefaultAudioResponse(id, accessToken = undefined) {
  await request('DELETE', `/api/v1/trigger/response/audio/${id}`,{ accessToken })
}

/**
 * Update trigger audio response
 * 
 * @param {string} id 
 * @param {{
 *   triggerId: string;
 *   guildId: string;
 *   file: File;
 * }} requestBody
 * @param {string|undefined} accessToken 
 * 
 * @returns {Promise<void>}
 */
export async function updateTriggerDefaultAudioResponse(id, requestBody, accessToken = undefined) {
  const formData = new FormData()
  Object.entries(requestBody).forEach(([key, value]) => formData.append(key, value))

  await request('PUT', `/api/v1/trigger/response/audio/${id}`, {
    body: formData,
    headers: {
      'Content-Type': undefined
    },
    accessToken
  })
}

/**
 * Get trigger response audio by id
 * 
 * @param {string} id 
 * @param {string|undefined} accessToken
 * 
 * @returns {Promise<object|null>}
 */
export async function fetchTriggerDefaultAudioResponse(id, accessToken = undefined) {
  return await request('GET', `/api/v1/trigger/response/audio/${id}`, { accessToken })
}

/**
 * Get trigger including the responses
 * 
 * @param {string} id 
 * @param {string|undefined} accessToken
 * 
 * @returns {Promise<object|null>} 
 */
export async function fetchTriggerByIdWithResponses(id, accessToken = undefined) {
  const trigger = await request('GET', `/api/v1/trigger/${id}`, { accessToken })

  if (!trigger) {
    return null
  }

  const responses = {}

  if (trigger.responseTextId) {
    responses.text = await fetchTriggerTextResponse(trigger.responseTextId, accessToken) ?? undefined
  }

  if (trigger.responseAudioId) {
    responses.audio = await fetchTriggerDefaultAudioResponse(trigger.responseAudioId, accessToken) ?? undefined
  }

  trigger.responses = responses
  return trigger
}
