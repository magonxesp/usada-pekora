import { Trigger } from '../../../../modules/trigger/domain/trigger'
import Select from '../../../shared/form/Select'
import { useEffect, useState } from 'react'
import { SelectOption } from '../../../../modules/shared/domain/form/select-option'
import { Guild } from '../../../../modules/guild/domain/guild'
import { useDispatch } from 'react-redux'
import { setTriggers } from '../../../../store/slices/app-slice'


export default function UserGuildSelect() {
  const dispatch = useDispatch()
  const [options, setOptions] = useState([])

  useEffect(() => {
    fetch('/api/guild/user-guilds')
      .then(response => response.json())
      .then(items => setOptions(items.map((item: Guild): SelectOption => ({
        label: item.name,
        value: item.id,
        labelIcon: item.icon,
      }))))
  }, [])

  return (
    <>
      {(options.length > 0) ? (
        <Select options={options} onChange={(option) => {
          fetch(`/api/trigger/guild-triggers?id=${option.value}`)
            .then(response => response.json())
            .then(items => dispatch(setTriggers(items.map((item: object) => Object.assign(new (Trigger as any)(), item)))))
        }} />
      ) : (
        <div role="status" className="animate-pulse">
          <div className="w-72 h-5 bg-gray-200 rounded-full dark:bg-gray-700"></div>
        </div>
      )}
    </>
  )
}
