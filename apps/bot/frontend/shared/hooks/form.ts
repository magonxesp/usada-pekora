import { ChangeEvent, useEffect, useState } from 'react'
import { FormErrors, Validator, Validators, FormData } from '../helpers/form/validator'

type Input = HTMLInputElement
  | HTMLSelectElement
  | HTMLTextAreaElement

export function useFormData<T>(initialFormData: T) {
  const [formData, setFormData] = useState(initialFormData)

  const handleValueChange = (event: ChangeEvent<Input>) => {
    const name = event.target.name
    const value =  event.target.value

    setFormData({
      ...formData,
      [name]: value
    })
  }

  return {
    formData,
    handleValueChange
  }
}

export function useValidator<T extends FormData>(validators: Validators, formData: T) {
  const [errors, setErrors] = useState<FormErrors>({})
  const validator = new Validator(validators)

  const cleanErrors = () => {
    validator.cleanErrors()
    setErrors(validator.getErrors())
  }

  const validate = () => {
    cleanErrors()
    validator.validateAll(formData)
    setErrors(validator.getErrors())
  }

  useEffect(() => validate(), [formData])

  return {
    errors,
    cleanErrors,
    validate,
  }
}
