import { useEffect, useState } from 'react'
import { Guild } from '../../../../shared/domain/guild'
import { useDispatch } from 'react-redux'
import { setCurrentGuild, setUserGuilds } from '../../../../store/slices/app-slice'
import { useAppSelector } from '../../../../shared/hooks/store'
import IconSelect, { Option } from '../../../common/form/IconSelect/IconSelect'
import styles from './UserGuildSelect.module.css'
import LoadingSkeletonElement from '../../../common/loading/LoadingSkeletonElement/LoadingSkeletonElement'

export default function UserGuildSelect() {
  const dispatch = useDispatch()
  const [options, setOptions] = useState<Option[]>([])
  const selectedGuild = useAppSelector(selector => selector.app.selectedGuild)
  const userGuilds = useAppSelector(selector => selector.app.userGuilds)

  useEffect(() => {
    fetch('/api/guild/user-guilds')
      .then(response => response.json())
      .then(guilds => dispatch(setUserGuilds(guilds)))
  }, [])

  useEffect(() => {
    setOptions(userGuilds.map((item: Guild): Option => ({
      label: item.name,
      value: item.id,
      icon: item.icon ?? "",
    })))
  }, [userGuilds])

  return (
    <LoadingSkeletonElement loaded={options.length > 0}>
      <IconSelect
        options={options}
        selected={selectedGuild}
        onChange={(option) => dispatch(setCurrentGuild({ id: String(option.value) }))}
        className={styles.select}
      />
    </LoadingSkeletonElement>
  )
}
