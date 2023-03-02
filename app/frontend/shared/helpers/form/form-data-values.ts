import { default as ServerFormData } from 'form-data'
import { Readable } from 'stream';

export abstract class FormDataValues {

  abstract toPlainObject(): object

  toFormData(): FormData {
    const formData = new FormData()

    Object.entries(this.toPlainObject()).forEach(([key, value]) => {
      if (value != null) {
        formData.append(key, value as string|File)
      }
    })

    return formData
  }

  toServerSideFormData(): ServerFormData {
    const formData = new ServerFormData()

    Object.entries(this.toPlainObject()).forEach(([key, value]) => {
      if (value != null && value instanceof Buffer) {
        formData.append(key, Readable.from(value))
      } else if (value != null) {
        formData.append(key, value)
      }
    })

    return formData
  }

}
