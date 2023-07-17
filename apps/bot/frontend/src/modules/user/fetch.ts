import { request } from '../shared/api'
import { User } from '@usada-pekora/shared-user'

export async function fetchCurrentUser(accessToken: string|undefined = undefined): Promise<User|null> {
  return await request<User>('GET', `/api/v1/user/me`, { accessToken })
}
