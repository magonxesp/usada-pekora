import { useModalStore } from '../store/modal'
import { ReactElement } from 'react'

export function useModal() {
  const showModal = useModalStore(state => state.showModal)
  const closeModal = useModalStore(state => state.closeModal)

  const show = (component: ReactElement) => showModal(component)
  const close = () => closeModal()

  return { show, close }
}
