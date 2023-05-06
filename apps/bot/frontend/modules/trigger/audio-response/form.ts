import { v4 as uuidv4 } from 'uuid'

export interface TriggerAudioResponseFormData {
  id: string
  content: File | null,
  provider: string
}

export const emptyTriggerResponseAudioFormData = (): TriggerAudioResponseFormData => ({
  id: uuidv4(),
  content: null,
  provider: 'default'
})
