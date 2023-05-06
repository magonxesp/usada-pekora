import { TriggerResponse } from './fetch'
import { TriggerTextResponseFindResponse } from './text-response/fetch-default'
import { TriggerDefaultAudioFindResponse } from './audio-response/fetch-default'

export interface TriggerResponses {
  text?: TriggerTextResponseFindResponse
  audio?: TriggerDefaultAudioFindResponse
}

export interface Trigger extends TriggerResponse {
  responses?: TriggerResponses
}

export const triggerCompare = {
  contains: 'in',
  pattern: 'pattern'
}

