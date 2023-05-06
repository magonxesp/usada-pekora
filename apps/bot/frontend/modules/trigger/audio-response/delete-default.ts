import axios from 'axios'
import { backendUrl, headers } from '../../shared/client/backend'

export async function deleteTriggerDefaultAudioResponse(id: string) {
  await axios.delete(backendUrl(`/api/v1/trigger/response/audio/${id}`), {
    headers: headers()
  })
}
