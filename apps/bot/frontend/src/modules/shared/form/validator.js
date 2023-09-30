export class Validator {

  #errors = {}
  #validators = {}

  constructor(validators) {
    this.#validators = validators
  }

  #addError(name, errorMessage) {
    const errors = this.#errors[name] || []
    errors.push(errorMessage)
    this.#errors[name] = errors
  }

  validate(inputName, value) {
    if (typeof this.#validators[inputName] !== 'undefined') {
      const validators = this.#validators[inputName]

      Object.entries(validators).forEach(([name, validator]) => {
        const skipValidator = (validator.skip ?? (() => false))()

        if (!skipValidator && !validator.validate(value)) {
          this.#addError(inputName, validator.errorMessage)
        }
      })
    }
  }

  validateAll(formData) {
    Object.entries(formData).forEach(([inputName, value]) => {
      this.validate(inputName, value)
    })
  }

  cleanErrorsOf(name) {
    if (typeof this.#errors[name] !== 'undefined') {
      this.#errors[name] = []
    }
  }

  cleanErrors() {
    this.#errors = {}
  }

  getErrors() {
    return this.#errors
  }

}
