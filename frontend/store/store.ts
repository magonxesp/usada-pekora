import { configureStore } from '@reduxjs/toolkit'
import discordReducer from './slices/discord-slice'
import triggerReducer from './slices/trigger-slice'

const store = configureStore({
  reducer: {
    discord: discordReducer,
    trigger: triggerReducer
  },
})

export default store
// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch
