import { toast, ToastOptions, ToastPromiseParams, TypeOptions } from 'react-toastify'

/**
 * Toast default options
 * 
 * @type {ToastOptions}
 */
const defaultToastOptions = {
  hideProgressBar: true,
  autoClose: 2000,
  position: "bottom-right",
}

/**
 * Show alert
 * 
 * @param {string} content 
 * @param {TypeOptions} type 
 */
export function alert(content, type) {
  toast(content, {
    type,
    ...defaultToastOptions
  })
}

/**
 * Show alert by promise
 * 
 * @param {Promise} promise 
 * @param {ToastPromiseParams} messages 
 * @returns {Promise}
 */
export async function asyncAlert(promise, messages) {
  return await toast.promise(promise, messages, defaultToastOptions)
}
