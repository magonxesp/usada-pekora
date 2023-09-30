import { useEffect, useState } from 'react'
import { FormData, FormErrors, Validator, Validators } from './validator'

/**
 * Validate the form data object
 * 
 * @param {object} validators 
 * @param {object} formData 
 * @returns 
 */
export function useValidator(validators, formData) {
  const [errors, setErrors] = useState({})
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

  const validateSingle = (name, value) => {
    validator.cleanErrorsOf(name)
    validator.validate(name, value)
    setErrors(validator.getErrors())
  }

  return { errors, cleanErrors, validate, validateSingle }
}

export function useEmitOnChange(props, formData) {
  useEffect(() => {
    typeof props.onChange !== 'undefined' && props.onChange(formData)
  }, [formData])
}
