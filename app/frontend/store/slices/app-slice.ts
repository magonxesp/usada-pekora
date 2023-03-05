import { createSlice, PayloadAction } from '@reduxjs/toolkit'
import { Trigger } from '../../shared/domain/trigger'
import { Guild } from '../../shared/domain/guild'
import { ReactElement } from 'react'

interface ModalViewState {
  component?: ReactElement
  show: boolean
}

interface SliceState {
  userGuilds: Guild[],
  selectedGuild: string,
  triggers: Array<Trigger>,
  modal: ModalViewState
}

const initialModalState: ModalViewState = {
  show: false
}

const initialState: SliceState = {
  userGuilds: [],
  selectedGuild: "",
  triggers: [],
  modal: initialModalState
}

export const appSlice = createSlice({
  name: 'app',
  initialState,
  reducers: {
    setCurrentGuild(state, action: PayloadAction<{id: string, storeOnSessionStorage?: boolean}>) {
      state.selectedGuild = action.payload.id

      if (action.payload.storeOnSessionStorage ?? true) {
        sessionStorage.setItem("current_selected_guild", state.selectedGuild)
      }
    },
    setTriggers(state, action: PayloadAction<Array<Trigger>>) {
      state.triggers = action.payload
    },
    setUserGuilds(state, action: PayloadAction<Array<Guild>>) {
      state.userGuilds = action.payload
    },
    showModal(state, action: PayloadAction<ReactElement>) {
      state.modal.component = action.payload
      state.modal.show = true

      const body = document.querySelector("body")

      if (body) {
        body.style.overflow = 'hidden'
      }
    },
    closeModal(state) {
      state.modal.show = false

      const body = document.querySelector('body')

      if (body) {
        body.style.overflow = ''
      }
    }
  }
})

export const {
  setCurrentGuild,
  setTriggers,
  setUserGuilds,
  showModal,
  closeModal,
} = appSlice.actions
export default appSlice.reducer
