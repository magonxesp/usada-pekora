import { v4 as uuidv4 } from 'uuid'
import { triggerCompare } from './trigger'
import { triggerTextResponseToFormData } from './trigger-text-response'
import { triggerDefaultAudioResponseToFormData } from './trigger-audio-response'

export const emptyTriggerFormData = () => ({
  id: uuidv4(),
  title: '',
  input: '',
  compare: triggerCompare.contains,
  guildId: ''
})

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
