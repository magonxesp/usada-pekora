import { useAppSelector } from './store'
import { useEffect, useState } from 'react'

export function useSelectedGuild(): string {
  const selectedGuild = useAppSelector(selector => selector.app.selectedGuild)
  const userGuilds = useAppSelector(selector => selector.app.userGuilds)
  const [selected, setSelected] = useState("")

  useEffect(() => {
    if (selectedGuild != "") {
      setSelected(selectedGuild)
    } else if (userGuilds.length > 0) {
      setSelected(String(userGuilds[0].id))
    }
  }, [selectedGuild, userGuilds])

  return selected
}
