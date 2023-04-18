import { FormData } from './validator'

export interface FormGroupProps<T extends FormData> {
  data?: T
  onChange?: (data: T) => void
}
