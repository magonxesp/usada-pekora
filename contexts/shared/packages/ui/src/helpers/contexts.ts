import { createContext } from 'react'
import { User } from '@usada-pekora/shared-user'

export const UserContext = createContext<User|null>(null)
