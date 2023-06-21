import { useEffect, useState } from 'react'
import { Guild } from '../../../../modules/guild/guild'
import styles from './UserGuildSelect.module.css'
import { IconSelect, IconSelectOption, LoadingSkeletonElement } from '@usada-pekora/shared-ui'
import { useGuilds, useSelectedGuild } from '../../../../modules/guild/hooks'

export default function UserGuildSelect() {
  const [options, setOptions] = useState<IconSelectOption[]>([])
  const { selected, select } = useSelectedGuild()
  const userGuilds = useGuilds()

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
        defaultValue={(selected === '') ? undefined : selected}
        onChange={(option) => select(String(option))}
        className={styles.select}
      />
    </LoadingSkeletonElement>
  )
}
