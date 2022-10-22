import { createSlice, PayloadAction } from '@reduxjs/toolkit'
import { Trigger } from '../../modules/trigger/domain/trigger'
import { Guild } from '../../modules/guild/domain/guild'

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
    },
    setTriggers(state, action: PayloadAction<Array<Trigger>>) {
      state.triggers = action.payload
    }
  }
})

export const { setCurrentGuild, setTriggers } = appSlice.actions
export default appSlice.reducer
