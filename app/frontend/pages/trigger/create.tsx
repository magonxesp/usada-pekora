import { NextPage } from 'next'
import TriggerForm from '../../components/domain/trigger/form/TriggerForm'
import GuildTriggersView from '../../components/views/guild-triggers-view/GuildTriggersView'
import Sidebar from '../../components/shared/sidebar/Sidebar'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/router'
import { useIntl } from 'react-intl'
import { asyncAlert } from '../../shared/helpers/alert'
import { emptyTriggerFormData, submitTriggerCreateRequest, TriggerFormData } from '../../shared/helpers/form/trigger'
import TriggerFormSidebar from '../../components/domain/trigger/form/TriggerFormSidebar'

const NewTrigger: NextPage = () => {
  const router = useRouter()
  const intl = useIntl()

  const closeSidebar = () => {
    setTimeout(async () => await router.push("/"), 500)
  }

  const createTrigger = async (data: TriggerFormData): Promise<void> => {
    console.log(data)
    const request = submitTriggerCreateRequest(data)

    await asyncAlert(request, {
      success: intl.$t({id: 'trigger.form.create.success'}),
      error: intl.$t({ id: 'trigger.form.create.error' }),
      pending: intl.$t({ id: 'trigger.form.create.loading' }),
    })
  }

  return (
    <>
      <GuildTriggersView />
      <TriggerFormSidebar
        title={intl.$t({ id: 'trigger.form.sidebar.create.title' })}
        onSubmit={createTrigger}
        onSidebarClose={closeSidebar}
      />
    </>
  )
}

export default NewTrigger
