import { default as ServerFormData } from 'form-data'
import { Readable } from 'stream'

export function objectToFormData(object: object) {
  const formData = new FormData()

  Object.entries(object).forEach(([key, value]) => {
    if (value != null) {
      formData.append(key, value as string|File)
    }
  })

  return formData
}

export function objectToFormDataServerSide(object: object): ServerFormData {
  const formData = new ServerFormData()

  Object.entries(object).forEach(([key, value]) => {
    if (value != null && value instanceof Buffer) {
      formData.append(key, Readable.from(value))
    } else if (value != null) {
      formData.append(key, value)
    }
  })

  return formData
}
