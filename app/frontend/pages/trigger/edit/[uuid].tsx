import { NextPage } from 'next'
import GuildTriggersView from '../../../components/views/guild-triggers-view/GuildTriggersView'

const TriggerEdit: NextPage = ()  => {
  return (
    <>
      <GuildTriggersView />
    </>
  )

  // TODO: poner sidebar con el componente de formulario para editar el trigger
}

export default TriggerEdit
