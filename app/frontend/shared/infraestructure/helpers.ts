import { toast as reactToast, TypeOptions } from 'react-toastify'

export function toast(content: string, type: TypeOptions) {
  reactToast(content, {
    hideProgressBar: true,
    autoClose: 2000,
    type,
    position: "bottom-right"
  })
}
