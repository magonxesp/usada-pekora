import { v4 as uuidv4 } from 'uuid'
import { TriggerCompare } from '../../../domain/trigger'

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

export interface TriggerFormData extends TriggerEntityFormData {
  id: string,
  responseText?: TriggerTextResponseFormData,
  responseAudio: File|Buffer|null,
  discordGuildId: string
}

export const emptyTriggerFormData = (): TriggerFormData => ({
  id: uuidv4(),
  title: "",
  input: "",
  compare: TriggerCompare.CONTAINS,
  responseAudio: null,
  discordGuildId: ""
})

export const emptyTriggerResponseTextFormData = (): TriggerTextResponseFormData => ({
  id: uuidv4(),
  content: "",
  type: "text"
})
