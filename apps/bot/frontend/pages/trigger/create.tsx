import { NextPage } from 'next'
import GuildTriggersView from '../../components/views/GuildTriggersView/GuildTriggersView'
import { useRouter } from 'next/router'
import { useIntl } from 'react-intl'
import { asyncAlert } from '../../shared/helpers/alert'
import { submitTriggerCreateRequest, TriggerFormData } from '../../shared/helpers/form/trigger/trigger'
import TriggerFormSidebar from '../../components/domain/trigger/TriggerFormSidebar/TriggerFormSidebar'

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
