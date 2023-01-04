import { NextPage } from 'next'
import GuildTriggersView from '../../../components/views/guild-triggers-view/GuildTriggersView'
import { Trigger, TriggerCompare } from '../../../shared/domain/trigger'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/router'
import Sidebar from '../../../components/shared/sidebar/Sidebar'
import TriggerForm from '../../../components/domain/trigger/form/TriggerForm'

const TriggerEdit: NextPage = ()  => {
  const trigger = Trigger.fromPrimitives({
    uuid: "fee1ff15-67fc-4cb6-b464-9a567549d0fb",
    title: "Lorem fistrum está la cosa muy malar caballo",
    compare: TriggerCompare.CONTAINS,
    discordServerId: "222354445645",
    input: "chiquito",
  })

  const [isOpened, setIsOpened] = useState(false)
  const router = useRouter()

  useEffect(() => {
    setTimeout(() => setIsOpened(true), 100)
  }, [])

  const closeSidebar = () => {
    setIsOpened(false)
    setTimeout(async () => await router.push("/"), 500)
  }

  return (
    <>
      <GuildTriggersView />
      <Sidebar show={isOpened}>
        <Sidebar.Header onClose={closeSidebar}>
          <h2 className="heading-2 py-0">
            Editar trigger
          </h2>
        </Sidebar.Header>
        <Sidebar.Body>
          <TriggerForm trigger={trigger} />
        </Sidebar.Body>
      </Sidebar>
    </>
  )

  // TODO: poner sidebar con el componente de formulario para editar el trigger
}

export default TriggerEdit
