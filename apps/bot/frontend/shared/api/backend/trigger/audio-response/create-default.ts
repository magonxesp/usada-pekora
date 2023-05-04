import axios, { toFormData } from 'axios/index'
import { backendUrl, headers } from '../../backend'

interface TriggerDefaultAudioResponseCreateRequest {
  id: string
  file: File | Buffer
  triggerId: string
  guildId: string
}

export async function createTriggerAudio(audio: TriggerDefaultAudioResponseCreateRequest) {
  await axios.post(backendUrl('/api/v1/trigger/audio'), toFormData(audio), {
    headers: headers('multipart/form-data')
  })
}
