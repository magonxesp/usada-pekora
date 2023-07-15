import { backendUrl, headers } from '../shared/client/backend'
import { User } from '@usada-pekora/shared-user'
import { NextRequest } from 'next/server'

export async function fetchCurrentUser(request: NextRequest|null = null): Promise<User|null> {
  try {
    const response = await fetch(backendUrl(`/api/v1/user/me`), {
      headers: headers(request)
    })

    return await response.json() as User
  } catch (exception) {
    return null
  }
}
