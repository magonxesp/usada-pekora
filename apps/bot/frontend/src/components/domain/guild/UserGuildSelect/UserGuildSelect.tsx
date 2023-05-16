import { useEffect, useState } from 'react'
import { Guild } from '../../../../modules/guild/guild'
import { useDispatch } from 'react-redux'
import { setCurrentGuild, setUserGuilds } from '../../../../store/slices/app-slice'
import { useAppSelector } from '../../../../modules/shared/hooks'
import styles from './UserGuildSelect.module.css'
import { LoadingSkeletonElement, IconSelect, IconSelectOption } from '@usada-pekora/shared-ui'

export default function UserGuildSelect() {
  const dispatch = useDispatch()
  const [options, setOptions] = useState<IconSelectOption[]>([])
  const selectedGuild = useAppSelector(selector => selector.app.selectedGuild)
  const userGuilds = useAppSelector(selector => selector.app.userGuilds)

  useEffect(() => {
    fetch('/api/guild/user-guilds')
      .then(response => response.json())
      .then(guilds => dispatch(setUserGuilds(guilds)))
  }, [])

  useEffect(() => {
    setOptions(userGuilds.map((item: Guild): IconSelectOption => ({
      label: item.name,
      value: item.id,
      icon: item.icon ?? "",
    })))
  }, [userGuilds])

  return (
    <LoadingSkeletonElement loaded={options.length > 0}>
      <IconSelect
        options={options}
        selected={(selectedGuild === '') ? undefined : selectedGuild}
        onChange={(option) => dispatch(setCurrentGuild({ id: String(option) }))}
        className={styles.select}
      />
    </LoadingSkeletonElement>
  )
}
