export type FormErrors = {
  [name: string]: Array<string>
}

export type FormData = {
  [name: string]: any
}

export type Validators = {
  [name: string]: {
    [validator: string]: {
      validate: (value: unknown) => boolean,
      errorMessage: string,
      skip?: () => boolean
    }
  }
}

export class Validator {

  private errors: FormErrors

  constructor(private validators: Validators) {
    this.errors = {}
  }

  private addError(name: string, errorMessage: string) {
    const errors = this.errors[name] || []
    errors.push(errorMessage)
    this.errors[name] = errors
  }

  validate(inputName: string, value: unknown) {
    if (typeof this.validators[inputName] !== 'undefined') {
      const validators = this.validators[inputName]

      Object.entries(validators).forEach(([name, validator]) => {
        const skipValidator = (validator.skip ?? (() => false))()

        if (!skipValidator && !validator.validate(value)) {
          this.addError(inputName, validator.errorMessage)
        }
      })
    }
  }

  validateAll(formData: FormData) {
    Object.entries(formData).forEach(([inputName, value]) => {
      this.validate(inputName, value)
    })
  }

  cleanErrorsOf(name: string) {
    if (typeof this.errors[name] !== 'undefined') {
      this.errors[name] = []
    }
  }

  cleanErrors() {
    this.errors = {}
  }

  getErrors(): FormData {
    return this.errors
  }

}
