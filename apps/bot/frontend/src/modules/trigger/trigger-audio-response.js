import { v4 as uuidv4 } from 'uuid'

export const emptyTriggerResponseAudioFormData = () => ({
  id: uuidv4(),
  content: null,
})

export function triggerDefaultAudioResponseToFormData(audio) {
  const audioFileName = audio.source.split(/\/|\\/).reverse()[0]

  return {
    id: audio.id,
    content: (typeof File !== 'undefined') 
      ? new File([], audioFileName)
      : audioFileName,
  }
}
