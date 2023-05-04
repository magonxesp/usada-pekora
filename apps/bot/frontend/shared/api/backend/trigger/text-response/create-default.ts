import axios from 'axios'
import { backendUrl, headers } from '../../backend'

interface TriggerTextResponseCreateRequest {
  id: string,
  content: string,
  type: string
}

export async function createTriggerTextResponse(textResponse: TriggerTextResponseCreateRequest) {
  await axios.post(backendUrl('/api/v1/trigger/response/text'), textResponse, {
    headers: headers()
  })
}
