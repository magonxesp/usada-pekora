import TriggerList from '../components/domain/trigger/list/TriggerList'
import EmptyState from '../components/shared/empty-state/EmptyState'
import Button from '../components/shared/form/Button'
import UserGuildSelect from '../components/domain/guild/select/UserGuildSelect'
import { NextPage } from 'next'
import { useAppSelector } from '../hooks'
import { useEffect } from 'react'
import { Trigger } from '../modules/trigger/domain/trigger'
import { setCurrentGuild, setTriggers } from '../store/slices/app-slice'
import { useDispatch } from 'react-redux'


const Home: NextPage = () => {
  const dispatch = useDispatch();
  const triggers = useAppSelector((state) => state.app.triggers)
  const selectedGuild = useAppSelector((state) => state.app.selectedGuild)

  useEffect(() => {
    let guildId

    if ((guildId = sessionStorage.getItem('current_selected_guild')) != null) {
      dispatch(setCurrentGuild(guildId))
    }
  },[])

  useEffect(() => {
    fetch(`/api/trigger/guild-triggers?id=${selectedGuild}`)
      .then(response => response.json())
      .then(items => dispatch(setTriggers(items.map((item: object) => Object.assign(new (Trigger as any)(), item)))))
  }, [selectedGuild])

  return (
    <div>
      <UserGuildSelect />
      <div className="py-5">
        <div className="flex justify-between items-center">
          <h1 className="heading-1">Reacciones</h1>
          <Button color="primary">Añadir</Button>
        </div>
        {triggers.length > 0 ? (
          <TriggerList items={triggers} />
        ) : (
          <EmptyState />
        )}
      </div>
    </div>
  )
}

export default Home
