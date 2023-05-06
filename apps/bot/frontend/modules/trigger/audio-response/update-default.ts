import axios, { toFormData } from 'axios'
import { backendUrl, headers } from '../../shared/client/backend'

interface TriggerDefaultAudioResponseUpdateRequestNewValues {
  triggerId?: string;
  guildId?: string;
  file?: File;
}

export interface TriggerDefaultAudioResponseUpdateRequest {
  id: string;
  values: TriggerDefaultAudioResponseUpdateRequestNewValues
}

export async function updateTriggerDefaultAudioResponse(request: TriggerDefaultAudioResponseUpdateRequest) {
  await axios.put(backendUrl(`/api/v1/trigger/response/audio/${request.id}`), toFormData(request.values), {
    headers: headers()
  })
}
