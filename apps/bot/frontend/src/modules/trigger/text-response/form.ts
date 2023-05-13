import { v4 as uuidv4 } from 'uuid'
import { TriggerDefaultAudioFindResponse } from '../audio-response/fetch-default'
import { TriggerAudioResponseFormData } from '../audio-response/form'
import { TriggerTextResponseFindResponse } from './fetch-default'

export interface TriggerTextResponseFormData {
  id: string
  content: string
  type: string
}

export const emptyTriggerResponseTextFormData = (): TriggerTextResponseFormData => ({
  id: uuidv4(),
  content: '',
  type: 'text'
})

export function triggerTextResponseToFormData(text: TriggerTextResponseFindResponse): TriggerTextResponseFormData {
  return {
    id: text.id,
    content: text.content,
    type: text.type
  }
}
