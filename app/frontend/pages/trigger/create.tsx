import { NextPage } from 'next'
import TriggerForm from '../../components/domain/trigger/form/TriggerForm'
import { Trigger } from '../../shared/domain/trigger'
import GuildTriggersView from '../../components/views/guild-triggers-view/GuildTriggersView'
import Sidebar from '../../components/shared/sidebar/Sidebar'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/router'
import { useIntl } from 'react-intl'

const NewTrigger: NextPage = () => {
  const [isOpened, setIsOpened] = useState(false)
  const router = useRouter()
  const intl = useIntl()

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
            {intl.$t({ id: 'trigger.form.sidebar.create.title' })}
          </h2>
        </Sidebar.Header>
        <Sidebar.Body>
          <TriggerForm trigger={Trigger.empty()} />
        </Sidebar.Body>
      </Sidebar>
    </>
  )
}

export default NewTrigger
