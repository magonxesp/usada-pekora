import { NextPage } from 'next'
import TriggerForm from '../../components/domain/trigger/form/TriggerForm'
import { Trigger, TriggerCompare } from '../../shared/domain/trigger'
import GuildTriggersView from '../../components/views/guild-triggers-view/GuildTriggersView'
import Sidebar from '../../components/shared/sidebar/Sidebar'

const NewTrigger: NextPage = () => {
  const trigger = Trigger.fromPrimitives({
    uuid: "fee1ff15-67fc-4cb6-b464-9a567549d0fb",
    title: "Lorem fistrum está la cosa muy malar caballo",
    compare: TriggerCompare.CONTAINS,
    discordServerId: "222354445645",
    input: "chiquito",
  })
  // TODO: poner sidebar con el componente de formulario para crear el trigger
  return (
    <>
      <GuildTriggersView />
      <Sidebar>
        <Sidebar.Body>
          <TriggerForm trigger={trigger} />
        </Sidebar.Body>
        <Sidebar.Body>
          <TriggerForm trigger={trigger} />
        </Sidebar.Body>
      </Sidebar>
    </>
  )
}

export default NewTrigger
