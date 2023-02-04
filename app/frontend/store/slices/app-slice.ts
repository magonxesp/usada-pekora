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
    setCurrentGuild(state, action: PayloadAction<string>) {
      state.selectedGuild = action.payload
      sessionStorage.setItem("current_selected_guild", state.selectedGuild)
    },
    setTriggers(state, action: PayloadAction<Array<Trigger>>) {
      state.triggers = action.payload
    }
  }
})

export const {
  setCurrentGuild,
  setTriggers
} = appSlice.actions
export default appSlice.reducer
