import { TriggerTextResponse } from './trigger-text-response'
import { TriggerDefaultResponse } from './trigger-audio-response'

export type Trigger = {
  id: string
  title: string
  input: string
  compare: string
  responseTextId?: string
  responseAudioId?: string
  discordGuildId: string
  responses?: TriggerResponses
}

export type TriggerResponses = {
  text?: TriggerTextResponse
  audio?: TriggerDefaultResponse
}

export type TriggerCollection = {
  triggers: Trigger[]
}

export const triggerCompare = {
  contains: 'in',
  pattern: 'pattern'
}

