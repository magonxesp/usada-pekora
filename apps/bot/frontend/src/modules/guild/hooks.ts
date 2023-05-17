import { useEffect, useState } from 'react'
import { useAppStore } from '../../store/app'

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

  const select = (guildId: string) => {
    sessionStorage.setItem('current_selected_guild', guildId)
    setCurrentGuild(guildId)
  }

  return { selected, select }
}

export function useGuilds() {
  const guilds = useAppStore(state => state.guilds)
  const setGuilds = useAppStore(state => state.setGuilds)

  useEffect(() => {
    fetch('/api/guild/user-guilds')
      .then(response => response.json())
      .then(guilds => setGuilds(guilds))
  }, [])

  return guilds
}
