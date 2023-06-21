import { toast, ToastOptions, ToastPromiseParams, TypeOptions } from 'react-toastify'

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

export async function asyncAlert<T>(promise: Promise<T>, messages: ToastPromiseParams): Promise<T> {
  return await toast.promise(promise, messages, defaultToastOptions) as Promise<T>
}
