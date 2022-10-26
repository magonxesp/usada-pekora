import Select from '../../../shared/form/Select'
import { useEffect, useState } from 'react'
import { SelectOption } from '../../../../modules/shared/domain/form/select-option'
import { Guild } from '../../../../modules/guild/domain/guild'
import { useDispatch } from 'react-redux'
import { setCurrentGuild } from '../../../../store/slices/app-slice'
import { signOut, useSession } from 'next-auth/react'
import { useAppSelector } from '../../../../hooks'


export default function UserGuildSelect() {
  const dispatch = useDispatch()
  const [options, setOptions] = useState([])
  const { status } = useSession()
  const selectedGuild = useAppSelector(selector => selector.app.selectedGuild)

  useEffect(() => {
    fetch('/api/guild/user-guilds')
      .then(response => {
        if (response.status === 403 && status === "authenticated") {
          signOut()
        }

        return response.json()
      })
      .then(items => setOptions(items.map((item: Guild): SelectOption => ({
        label: item.name,
        value: item.id,
        labelIcon: item.icon,
      }))))
  }, [])

  return (
    <>
      {(options.length > 0) ? (
        <Select
          options={options}
          selected={selectedGuild}
          onChange={(option) => dispatch(setCurrentGuild(option.value))} />
      ) : (
        <div role="status" className="animate-pulse">
          <div className="w-72 h-5 bg-gray-200 rounded dark:bg-gray-600"></div>
        </div>
      )}
    </>
  )
}
