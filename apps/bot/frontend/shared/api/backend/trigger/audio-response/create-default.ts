import axios, { toFormData } from 'axios'
import { backendUrl, headers } from '../../backend'

interface TriggerDefaultAudioResponseCreateRequest {
  id: string
  file: File
  triggerId: string
  guildId: string
}

export async function createTriggerAudio(audio: TriggerDefaultAudioResponseCreateRequest) {
  await axios.post(backendUrl('/api/v1/trigger/response/audio'), toFormData(audio), {
    headers: headers('multipart/form-data')
  })
}
