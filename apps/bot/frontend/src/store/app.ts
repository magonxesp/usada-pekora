import { create } from 'zustand'
import { Guild } from '../modules/guild/guild'
import { Trigger } from '../modules/trigger/trigger'

interface AppState {
  guilds: Guild[],
  selectedGuild: string,
  triggers: Trigger[],
  setCurrentGuild: (guildId: string) => void
  setGuilds: (guilds: Guild[]) => void
  setTriggers: (triggers: Trigger[]) => void
}

export const useAppStore = create<AppState>((set) => ({
  guilds: [],
  selectedGuild: "",
  triggers: [],
  setCurrentGuild: (guildId: string) => set((state) => ({ ...state, selectedGuild: guildId })),
  setGuilds: (guilds: Guild[]) => set((state) => ({ ...state, guilds })),
  setTriggers: (triggers: Trigger[]) => set((state) => ({ ...state, triggers })),
}))
