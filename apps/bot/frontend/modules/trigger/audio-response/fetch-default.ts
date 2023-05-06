import axios from 'axios'
import { backendUrl, headers } from '../../shared/client/backend'
import { TriggerTextResponseFindResponse } from '../text-response/fetch-default'

export interface TriggerDefaultAudioFindResponse {
  id: string;
  triggerId: string;
  guildId: string;
  file: string;
}

export async function fetchTriggerDefaultAudioResponse(id: string): Promise<TriggerDefaultAudioFindResponse> {
  const response = await axios.get<TriggerDefaultAudioFindResponse>(backendUrl(`/api/v1/trigger/response/text/${id}`), {
    headers: headers()
  })

  return response.data
}
