import { FormErrors } from '../validator'

export interface TriggerFormGroupRef {
  validate: () => void
  cleanErrors: () => void
  errors: FormErrors
}
