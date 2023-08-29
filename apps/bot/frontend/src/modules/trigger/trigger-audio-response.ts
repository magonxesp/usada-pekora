import { v4 as uuidv4 } from 'uuid'

export type TriggerAudioResponse = {
  id: string
  kind: string
  source: string
}

export interface TriggerAudioResponseFormData {
  id: string
  content: File | string | null,
}

export const emptyTriggerResponseAudioFormData = (): TriggerAudioResponseFormData => ({
  id: uuidv4(),
  content: null,
})

export function triggerDefaultAudioResponseToFormData(audio: TriggerAudioResponse): TriggerAudioResponseFormData {
  const audioFileName = audio.source.split(/\/|\\/).reverse()[0]

  return {
    id: audio.id,
    content: (typeof File !== 'undefined') 
      ? new File([], audioFileName)
      : audioFileName,
  }
}
