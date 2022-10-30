export class FormValidateEvent extends Event {
  constructor(public readonly form: HTMLFormElement) {
    super('formValidateEvent')
  }
}
