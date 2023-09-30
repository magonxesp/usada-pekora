import { useEffect } from 'react'
import { useAppStore } from '../store/app'
import { fetchCurrentUserGuilds } from '../helpers/guild-api'

export function useSelectedGuild() {
  const selected = useAppStore(state => state.selectedGuild)
  const setCurrentGuild = useAppStore(state => state.setCurrentGuild)
  const guilds = useAppStore(state => state.guilds)

  useEffect(() => {
    const sessionSelectedGuild = sessionStorage.getItem('current_selected_guild')

    if (selected == "" && sessionSelectedGuild != null) {
      setCurrentGuild(sessionSelectedGuild)
    } else if (selected == "" && guilds.length > 0) {
      setCurrentGuild(String(guilds[0].id))
    }
  }, [guilds])

  const select = (guildId) => {
    sessionStorage.setItem('current_selected_guild', guildId)
    setCurrentGuild(guildId)
  }

  return { selected, select }
}

export function useGuilds() {
  const guilds = useAppStore(state => state.guilds)
  const setGuilds = useAppStore(state => state.setGuilds)

  useEffect(() => {
    fetchCurrentUserGuilds()
      .then(guilds => setGuilds(guilds))
  }, [])

  return guilds
}
