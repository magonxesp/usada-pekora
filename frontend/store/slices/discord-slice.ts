import { createSlice, PayloadAction } from '@reduxjs/toolkit'

interface SliceState {
  selectedGuild: string
}

const initialState: SliceState = {
  selectedGuild: ""
}

export const discordSlice = createSlice({
  name: 'discord',
  initialState,
  reducers: {
    setCurrentGuild(state, action: PayloadAction<string>) {
      state.selectedGuild = action.payload
    }
  }
})

export const { setCurrentGuild } = discordSlice.actions
export default discordSlice.reducer
