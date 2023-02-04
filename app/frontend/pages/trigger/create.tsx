import { NextPage } from 'next'
import TriggerForm from '../../components/domain/trigger/form/TriggerForm'
import { TriggerFormData } from '../../shared/trigger/form/trigger-form-data'
import GuildTriggersView from '../../components/views/guild-triggers-view/GuildTriggersView'
import Sidebar from '../../components/shared/sidebar/Sidebar'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/router'
import { useIntl } from 'react-intl'
import { asyncAlert } from '../../shared/helpers/alert'

const NewTrigger: NextPage = () => {
  const [isOpened, setIsOpened] = useState(false)
  const [disableSubmit, setDisableSubmit] = useState(false)
  const router = useRouter()
  const intl = useIntl()

  useEffect(() => {
    setTimeout(() => setIsOpened(true), 100)
  }, [])

  const closeSidebar = () => {
    setIsOpened(false)
    setTimeout(async () => await router.push("/"), 500)
  }

  const createTrigger = (trigger: TriggerFormData) => {
    setDisableSubmit(true)

    const request = fetch('/api/trigger/create', {
      method: 'POST',
      body: JSON.stringify(trigger)
    }).then((response) => (!response.ok) ? Promise.reject() : response)

    asyncAlert(request, {
      success: intl.$t({id: 'trigger.form.create.success'}),
      error: intl.$t({ id: 'trigger.form.create.error' }),
      pending: intl.$t({ id: 'trigger.form.create.loading' }),
    }).then(() => {
      closeSidebar()
    }).catch(() => {
      setDisableSubmit(false)
    })
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
          <TriggerForm
            trigger={TriggerFormData.new()}
            onSubmit={createTrigger}
            disableSubmit={disableSubmit}
          />
        </Sidebar.Body>
      </Sidebar>
    </>
  )
}

export default NewTrigger
