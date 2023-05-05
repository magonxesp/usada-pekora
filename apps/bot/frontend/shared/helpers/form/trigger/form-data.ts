import { v4 as uuidv4 } from 'uuid'
import { triggerCompare } from '../../../api/backend/trigger/trigger'

export interface TriggerEntityFormData {
  title: string
  compare: string
  input: string
}

export interface TriggerTextResponseFormData {
  id: string
  content: string
  type: string
}

export interface TriggerAudioResponseFormData {
  id: string
  content: File|null,
  provider: string
}

export interface TriggerFormData extends TriggerEntityFormData {
  id: string,
  responseText?: TriggerTextResponseFormData,
  responseAudio?: TriggerAudioResponseFormData,
  discordGuildId: string
}

export const emptyTriggerFormData = (): TriggerFormData => ({
  id: uuidv4(),
  title: "",
  input: "",
  compare: triggerCompare.contains,
  discordGuildId: ""
})

export const emptyTriggerResponseTextFormData = (): TriggerTextResponseFormData => ({
  id: uuidv4(),
  content: "",
  type: "text"
})

export const emptyTriggerResponseAudioFormData = (): TriggerAudioResponseFormData => ({
  id: uuidv4(),
  content: null,
  provider: "default"
})
