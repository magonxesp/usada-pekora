import { useDispatch } from 'react-redux'
import { useAppSelector } from '../../../shared/hooks/store'
import { useEffect, useState } from 'react'
import { setCurrentGuild, setTriggers } from '../../../store/slices/app-slice'
import { Trigger } from '../../../shared/domain/trigger'
import TriggerList from '../../domain/trigger/list/TriggerList'
import EmptyState from '../../shared/empty-state/EmptyState'
import Link from 'next/link'
import UserGuildSelect from '../../domain/guild/select/UserGuildSelect'
import { fetchGuildTriggers } from '../../../shared/api/backend/trigger'
import TriggerListSkeleton from '../../domain/trigger/list/TriggerListSkeleton'
import { useSelectedGuild } from '../../../shared/hooks/guilds'

export default function GuildTriggersView() {
  const dispatch = useDispatch();
  const triggers = useAppSelector((state) => state.app.triggers)
  const selectedGuild = useSelectedGuild()
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let guildId

    if ((guildId = sessionStorage.getItem('current_selected_guild')) != null) {
      dispatch(setCurrentGuild({ id: guildId }))
    }
  },[])

  useEffect(() => {
    if (!selectedGuild) {
      return
    }

    const loadingAnimationTimeout = setTimeout(() => setLoading(true), 100)

    fetchGuildTriggers(selectedGuild).then(items => {
      clearTimeout(loadingAnimationTimeout)
      setLoading(false)
      dispatch(setTriggers(items))
    })
  }, [selectedGuild])

  return (
    <>
      <UserGuildSelect />
      <div className="py-5">
        <div className="flex justify-between items-center">
          <h1 className="heading-1">Reacciones</h1>
          <Link href="/trigger/create">
            <button className="bg-primary">Añadir</button>
          </Link>
        </div>
        {!loading && triggers.length > 0 ? (
          <TriggerList items={triggers} />
        ) : !loading && triggers.length == 0 ? (
          <EmptyState />
        ) : (
          <TriggerListSkeleton />
        )}
      </div>
    </>
  )
}
