import { v4 as uuidv4 } from 'uuid'

export type TriggerDefaultResponse = {
  id: string
  triggerId: string
  guildId: string
  file: string
}

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

export function triggerDefaultAudioResponseToFormData(audio: TriggerDefaultResponse): TriggerAudioResponseFormData {
  return {
    id: audio.id,
    content: new File([], audio.file),
    provider: 'default'
  }
}
