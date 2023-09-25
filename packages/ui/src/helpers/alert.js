import { toast } from 'react-toastify'

const defaultToastOptions = {
  hideProgressBar: true,
  autoClose: 2000,
  position: "bottom-right"
}

export function alert(content, type) {
  toast(content, {
    type,
    ...defaultToastOptions
  })
}

export async function asyncAlert(promise, messages) {
  return await toast.promise(promise, messages, defaultToastOptions)
}
