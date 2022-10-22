import { createSlice, PayloadAction } from '@reduxjs/toolkit'
import { Trigger } from '../../modules/trigger/domain/trigger'

interface SliceState {
  triggers: Array<Trigger>
}

const initialState: SliceState = {
  triggers: []
}

export const triggersSlice = createSlice({
  name: 'trigger',
  initialState,
  reducers: {
    setTriggers(state, action: PayloadAction<Array<Trigger>>) {
      state.triggers = action.payload
    }
  }
})

export const { setTriggers } = triggersSlice.actions
export default triggersSlice.reducer
