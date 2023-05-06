import { v4 as uuidv4 } from 'uuid'

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
