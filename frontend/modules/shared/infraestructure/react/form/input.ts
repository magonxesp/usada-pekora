import { ChangeEvent } from 'react'

export interface InputChangeEventHandler {
  (event: ChangeEvent<HTMLInputElement|HTMLSelectElement>): void
}

export interface InputWrapperOnChangeEventHandler {
  (name: string, value: any): void
}
