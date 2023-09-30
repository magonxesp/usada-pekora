import { v4 as uuidv4 } from 'uuid'
import { triggerCompare } from './trigger-api'

export const emptyTriggerFormData = () => ({
  id: uuidv4(),
  title: '',
  input: '',
  compare: triggerCompare.contains,
  guildId: ''
})

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

export const emptyTriggerResponseTextFormData = () => ({
  id: uuidv4(),
  content: '',
  type: 'text'
})

export function triggerTextResponseToFormData(text) {
  return {
    id: text.id,
    content: text.content,
    type: text.type
  }
}

export function triggerToFormData(trigger) {
  const data = {
    id: trigger.id,
    title: trigger.title,
    compare: trigger.compare,
    input: trigger.input,
    guildId: trigger.guildId,
  }

  if (typeof trigger.responses?.text !== 'undefined') {
    data.responseText = triggerTextResponseToFormData(trigger.responses.text)
  }

  if (typeof trigger.responses?.audio !== 'undefined') {
    data.responseAudio = triggerDefaultAudioResponseToFormData(trigger.responses.audio)
  }

  return data
}
