import axios from 'axios'
import { backendUrl, headers } from '../../shared/client/backend'

export interface TriggerDefaultAudioFindResponse {
  id: string;
  triggerId: string;
  guildId: string;
  file: string;
}

export async function fetchTriggerDefaultAudioResponse(id: string): Promise<TriggerDefaultAudioFindResponse> {
  const response = await axios.get<TriggerDefaultAudioFindResponse>(backendUrl(`/api/v1/trigger/response/audio/${id}`), {
    headers: headers()
  })

  return response.data
}
