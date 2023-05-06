import axios from 'axios/index'
import { backendUrl, headers } from '../../shared/client/backend'

interface TriggerTextResponseUpdateRequestNewValues {
  content?: string;
  type?: string;
}

export interface TriggerTextResponseUpdateRequest {
  id: string,
  values: TriggerTextResponseUpdateRequestNewValues
}

export async function updateTriggerTextResponse(request: TriggerTextResponseUpdateRequest) {
  await axios.put(backendUrl(`/api/v1/trigger/response/text/${request.id}`), request.values, {
    headers: headers()
  })
}
