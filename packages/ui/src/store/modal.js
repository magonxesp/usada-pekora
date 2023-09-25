import { create } from 'zustand'

export const useModalStore = create((set) => ({
  component: null,
  show: false,
  showModal: (component) => set(() => ({ component, show: true })),
  closeModal: () => set(() => ({ show: false }))
}))
