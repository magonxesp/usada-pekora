import { toast, TypeOptions, ToastPromiseParams, ToastOptions } from 'react-toastify'

const defaultToastOptions: ToastOptions = {
  hideProgressBar: true,
  autoClose: 2000,
  position: "bottom-right"
}

export function alert(content: string, type: TypeOptions) {
  toast(content, {
    type,
    ...defaultToastOptions
  })
}

export async function asyncAlert(promise: Promise<unknown>, messages: ToastPromiseParams): Promise<unknown> {
  return await toast.promise(promise, messages, defaultToastOptions)
}
