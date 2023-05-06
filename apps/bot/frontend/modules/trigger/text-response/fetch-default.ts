import axios from 'axios'
import { backendUrl, headers } from '../../shared/client/backend'

export interface TriggerTextResponseFindResponse {
  id: string;
  content: string;
  type: string;
}

export async function fetchTriggerTextResponse(id: string): Promise<TriggerTextResponseFindResponse> {
  const response = await axios.get<TriggerTextResponseFindResponse>(backendUrl(`/api/v1/trigger/response/audio/${id}`), {
    headers: headers()
  })

  return response.data
}
