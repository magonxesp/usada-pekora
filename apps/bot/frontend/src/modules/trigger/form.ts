import { FormErrors } from '../shared/form/validator'
import { v4 as uuidv4 } from 'uuid'
import { Trigger, triggerCompare } from './trigger'
import { TriggerTextResponseFormData, triggerTextResponseToFormData } from './trigger-text-response'
import { TriggerAudioResponseFormData, triggerDefaultAudioResponseToFormData } from './trigger-audio-response'

export interface TriggerFormGroupRef {
  validate: () => void
  cleanErrors: () => void
  errors: FormErrors
}

export interface TriggerEntityFormData {
  title: string
  compare: string
  input: string
}

export interface TriggerFormData extends TriggerEntityFormData {
  id: string,
  responseText?: TriggerTextResponseFormData,
  responseAudio?: TriggerAudioResponseFormData,
  discordGuildId: string
}

export const emptyTriggerFormData = (): TriggerFormData => ({
  id: uuidv4(),
  title: '',
  input: '',
  compare: triggerCompare.contains,
  discordGuildId: ''
})

export function triggerToFormData(trigger: Trigger): TriggerFormData {
  const data: TriggerFormData = {
    id: trigger.id,
    title: trigger.title,
    compare: trigger.compare,
    input: trigger.input,
    discordGuildId: trigger.discordGuildId,
  }

  if (typeof trigger.responses?.text !== 'undefined') {
    data.responseText = triggerTextResponseToFormData(trigger.responses.text)
  }

  if (typeof trigger.responses?.audio !== 'undefined') {
    data.responseAudio = triggerDefaultAudioResponseToFormData(trigger.responses.audio)
  }

  return data
}
