import { useModalStore } from '../store/modal'

export function useModal() {
  const showModal = useModalStore(state => state.showModal)
  const closeModal = useModalStore(state => state.closeModal)

  const show = (component) => showModal(component)
  const close = () => closeModal()

  return { show, close }
}
