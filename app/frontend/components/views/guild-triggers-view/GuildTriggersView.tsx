import { useDispatch } from 'react-redux'
import { useAppSelector } from '../../../hooks'
import { useEffect, useState } from 'react'
import { setCurrentGuild, setTriggers } from '../../../store/slices/app-slice'
import { Trigger } from '../../../shared/domain/trigger'
import Button from '../../shared/form/Button'
import TriggerList from '../../domain/trigger/list/TriggerList'
import EmptyState from '../../shared/empty-state/EmptyState'
import Link from 'next/link'
import UserGuildSelect from '../../domain/guild/select/UserGuildSelect'

export default function GuildTriggersView() {
  const dispatch = useDispatch();
  const triggers = useAppSelector((state) => state.app.triggers)
  const selectedGuild = useAppSelector((state) => state.app.selectedGuild)
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let guildId

    if ((guildId = sessionStorage.getItem('current_selected_guild')) != null) {
      dispatch(setCurrentGuild(guildId))
    }
  },[])

  useEffect(() => {
    const loadingAnimationTimeout = setTimeout(() => setLoading(true), 100)

    fetch(`/api/trigger/guild-triggers?id=${selectedGuild}`)
      .then(response => response.json())
      .then(items => {
        clearTimeout(loadingAnimationTimeout)
        setLoading(false)
        dispatch(setTriggers(items.map((item: object) => Trigger.fromPrimitives(item as any))))
      })
  }, [selectedGuild])

  return (
    <>
      <UserGuildSelect />
      <div className="py-5">
        <div className="flex justify-between items-center">
          <h1 className="heading-1">Reacciones</h1>
          <Link href="/trigger/create">
            <Button color="primary">Añadir</Button>
          </Link>
        </div>
        {!loading && triggers.length > 0 ? (
          <TriggerList items={triggers} />
        ) : !loading && triggers.length == 0 ? (
          <EmptyState />
        ) : (
          <div className="space-y-5">
            {Array.from(Array(5).keys()).map(() => (
              <div>
                <div role="status" className="animate-pulse mb-2">
                  <div className="w-full h-6 bg-gray-200 rounded dark:bg-gray-600"></div>
                </div>
                <div role="status" className="animate-pulse">
                  <div className="w-9/12 h-6 bg-gray-200 rounded dark:bg-gray-600"></div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </>
  )
}
