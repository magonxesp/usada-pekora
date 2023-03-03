import { NextPage } from 'next'
import TriggerForm from '../../components/domain/trigger/form/TriggerForm'
import GuildTriggersView from '../../components/views/guild-triggers-view/GuildTriggersView'
import Sidebar from '../../components/shared/sidebar/Sidebar'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/router'
import { useIntl } from 'react-intl'
import { asyncAlert } from '../../shared/helpers/alert'
import { emptyTriggerFormData, submitTriggerCreateRequest, TriggerFormData } from '../../shared/helpers/form/trigger'

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

  const createTrigger = (data: TriggerFormData) => {
    console.log(data)
    setDisableSubmit(true)
    const request = submitTriggerCreateRequest(data)

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
            triggerFormData={emptyTriggerFormData()}
            onSubmit={createTrigger}
            disableSubmit={disableSubmit}
          />
        </Sidebar.Body>
      </Sidebar>
    </>
  )
}

export default NewTrigger
