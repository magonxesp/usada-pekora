import { createSlice, PayloadAction } from '@reduxjs/toolkit'
import { Trigger } from '../../shared/trigger/trigger'
import { Guild } from '../../shared/guild/guild'

interface SliceState {
  userGuilds: Guild[],
  selectedGuild: string,
  triggers: Array<Trigger>
}

const initialState: SliceState = {
  userGuilds: [],
  selectedGuild: "",
  triggers: []
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
    }
  }
})

export const {
  setCurrentGuild,
  setTriggers,
  setUserGuilds
} = appSlice.actions
export default appSlice.reducer
