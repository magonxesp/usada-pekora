import Select from '../../../common/form/Select/Select'
import { useEffect, useState } from 'react'
import { SelectOption } from '../../../../shared/helpers/form/select-option'
import { Guild } from '../../../../shared/domain/guild'
import { useDispatch } from 'react-redux'
import { setCurrentGuild, setUserGuilds } from '../../../../store/slices/app-slice'
import { useAppSelector } from '../../../../shared/hooks/store'


export default function UserGuildSelect() {
  const dispatch = useDispatch()
  const [options, setOptions] = useState<SelectOption[]>([])
  const selectedGuild = useAppSelector(selector => selector.app.selectedGuild)
  const userGuilds = useAppSelector(selector => selector.app.userGuilds)

  useEffect(() => {
    fetch('/api/guild/user-guilds')
      .then(response => response.json())
      .then(guilds => dispatch(setUserGuilds(guilds)))
  }, [])

  useEffect(() => {
    setOptions(userGuilds.map((item: Guild): SelectOption => ({
      label: item.name,
      value: item.id,
      labelIcon: item.icon,
    })))
  }, [userGuilds])

  return (
    <>
      {(options.length > 0) ? (
        <Select
          options={options}
          selected={selectedGuild}
          onChange={(option) => dispatch(setCurrentGuild({ id: option.value }))} />
      ) : (
        <div role="status" className="animate-pulse">
          <div className="w-72 h-9 bg-gray-200 rounded dark:bg-gray-600"></div>
        </div>
      )}
    </>
  )
}
