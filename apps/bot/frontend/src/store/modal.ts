import { create } from 'zustand'
import { ReactElement } from 'react'
import { Guild } from '../modules/guild/guild'
import { Trigger } from '../modules/trigger/trigger'

interface ModalState {
  component?: ReactElement
  show: boolean
  showModal: (component: ReactElement) => void
  closeModal: () => void
}

export const useModalStore = create<ModalState>((set) => ({
  show: false,
  showModal: (component: ReactElement) => set(() => ({ component, show: true })),
  closeModal: () => set(() => ({ show: false }))
}))
