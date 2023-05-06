import axios from 'axios'
import { backendUrl, headers } from '../shared/client/backend'

export async function deleteTrigger(id: string) {
  await axios.delete(backendUrl(`/api/v1/trigger/${id}`), {
    headers: headers()
  })
}
