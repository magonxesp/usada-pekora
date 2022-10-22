import TriggerList from '../components/domain/trigger/list/TriggerList'
import EmptyState from '../components/shared/empty-state/EmptyState'
import Button from '../components/shared/form/Button'
import UserGuildSelect from '../components/domain/guild/select/UserGuildSelect'
import { NextPage } from 'next'
import { useAppSelector } from '../hooks'


const Home: NextPage = () => {
  const triggers = useAppSelector((state) => state.trigger.triggers)

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
