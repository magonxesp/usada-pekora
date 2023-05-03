import { ChangeEvent, useEffect, useState } from 'react'
import { FormErrors, Validator, Validators, FormData } from '../helpers/form/validator'
import { FormGroupProps } from '../helpers/form/props'

type Input = HTMLInputElement
  | HTMLSelectElement
  | HTMLTextAreaElement

export function useFormData<T extends FormData>(initialFormData: T) {
  const [formData, setFormData] = useState(initialFormData)

  const handleValueChange = (event: ChangeEvent<Input>) => {
    const input = event.currentTarget
    const name = input.name
    let value

    if (input.type === "file") {
      value = (input as HTMLInputElement).files
    } else {
      value = input.value
    }

    setFormData({
      ...formData,
      [name]: value
    })
  }

  return { formData, handleValueChange }
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

  const validateSingle = (name: string, value: unknown) => {
    validator.cleanErrorsOf(name)
    validator.validate(name, value)
    setErrors(validator.getErrors())
  }

  return { errors, cleanErrors, validate, validateSingle }
}

export function useEmitOnChange<T extends FormData>(props: FormGroupProps<T>, formData: T) {
  useEffect(() => {
    typeof props.onChange !== 'undefined' && props.onChange(formData)
  }, [formData])
}
