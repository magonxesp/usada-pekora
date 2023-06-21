import { useEffect, useState } from 'react'
import { FormData, FormErrors, Validator, Validators } from './validator'
import { FormGroupProps } from './props'

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
