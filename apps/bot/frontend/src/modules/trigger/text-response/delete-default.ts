import axios from 'axios'
import { backendUrl, headers } from '../../shared/client/backend'

export async function deleteTriggerTextResponse(id: string) {
  await axios.delete(backendUrl(`/api/v1/trigger/response/text/${id}`), {
    headers: headers()
  })
}
