import { useDispatch } from 'react-redux'
import { useAppSelector } from '../../../shared/hooks/store'
import { useEffect, useState } from 'react'
import { setCurrentGuild } from '../../../store/slices/app-slice'
import TriggerList from '../../domain/trigger/TriggerList/TriggerList'
import EmptyState from '../../common/states/EmptyState/EmptyState'
import Link from 'next/link'
import UserGuildSelect from '../../domain/guild/UserGuildSelect/UserGuildSelect'
import TriggerListSkeleton from '../../domain/trigger/TriggerListSkeleton/TriggerListSkeleton'
import { useSelectedGuild } from '../../../shared/hooks/guilds'
import { useFetchTriggers } from '../../../shared/hooks/fetch'

export default function GuildTriggersView() {
  const dispatch = useDispatch();
  const triggers = useAppSelector((state) => state.app.triggers)
  const selectedGuild = useSelectedGuild()
  const [loading, setLoading] = useState(true);
  const fetchTriggers = useFetchTriggers()

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

    fetchTriggers().then(() => {
      clearTimeout(loadingAnimationTimeout)
      setLoading(false)
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
