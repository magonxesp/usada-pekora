import axios from 'axios'
import { backendUrl, headers } from '../shared/client/backend'
import { User } from '@usada-pekora/shared-user'

interface UserResponse {
  id: string
  avatar: string|null
  name: string
  providerId: string
  provider: string
}

export async function fetchCurrentUser(): Promise<User|null> {
  try {
    const response = await axios.get<UserResponse>(backendUrl(`/api/v1/user/me`), {
      headers: headers()
    })

    return response.data as User
  } catch (exception) {
    return null
  }
}
