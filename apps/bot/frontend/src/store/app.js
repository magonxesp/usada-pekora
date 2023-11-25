import { create } from 'zustand'

export const useAppStore = create((set) => ({
  guilds: [],
  selectedGuild: "",
  triggers: [],
  error: null,
  setCurrentGuild: (guildId) => set((state) => ({ ...state, selectedGuild: guildId })),
  setGuilds: (guilds) => set((state) => ({ ...state, guilds })),
  setTriggers: (triggers) => set((state) => ({ ...state, triggers })),
  setError: (error) => set((state) => ({ ...state, error }))
}))
