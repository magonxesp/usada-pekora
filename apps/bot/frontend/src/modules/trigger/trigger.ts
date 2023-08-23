import { TriggerTextResponse } from './trigger-text-response'
import { TriggerAudioResponse } from './trigger-audio-response'

export type Trigger = {
  id: string
  title: string
  input: string
  compare: string
  responseTextId?: string
  responseAudioId?: string
  guildId: string
  responses?: TriggerResponses
}

export type TriggerResponses = {
  text?: TriggerTextResponse
  audio?: TriggerAudioResponse
}

export type TriggerCollection = {
  triggers: Trigger[]
}

export const triggerCompare = {
  contains: 'in',
  pattern: 'pattern'
}

