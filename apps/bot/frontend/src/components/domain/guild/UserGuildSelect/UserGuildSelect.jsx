import { useEffect, useState } from 'react'
import styles from './UserGuildSelect.module.css'
import { IconSelect, LoadingSkeletonElement } from '@usada-pekora/ui/components'
import { useGuilds, useSelectedGuild } from '../../../../modules/guild/hooks'

export default function UserGuildSelect() {
  const [options, setOptions] = useState([])
  const { selected, select } = useSelectedGuild()
  const userGuilds = useGuilds()

  useEffect(() => {
    setOptions(userGuilds.map((item) => ({
      label: item.name,
      value: item.id,
      icon: item.iconUrl ?? "",
    })))
  }, [userGuilds])

  return (
    <LoadingSkeletonElement loaded={options.length > 0}>
      <IconSelect
        options={options}
        defaultValue={(selected === '') ? undefined : selected}
        onChange={(option) => select(String(option))}
        className={styles.select}
      />
    </LoadingSkeletonElement>
  )
}
