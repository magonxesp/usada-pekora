import { FormErrors } from '../shared/form/validator'
import { v4 as uuidv4 } from 'uuid'
import { triggerCompare } from './trigger'
import { TriggerTextResponseFormData } from './text-response/form'
import { TriggerAudioResponseFormData } from './audio-response/form'

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
