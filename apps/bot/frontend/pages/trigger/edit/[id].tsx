import { GetServerSideProps, InferGetServerSidePropsType } from 'next'
import GuildTriggersView from '../../../components/views/GuildTriggersView/GuildTriggersView'
import { useRouter } from 'next/router'
import { useIntl } from 'react-intl'
import { asyncAlert } from '../../../modules/shared/alert'
import TriggerFormSidebar from '../../../components/domain/trigger/TriggerFormSidebar/TriggerFormSidebar'
import { submitTriggerUpdateRequest } from '../../../modules/trigger/form/trigger'
import { Trigger } from '../../../modules/trigger/trigger'
import { fetchTriggerByIdWithResponses } from '../../../modules/trigger/fetch'
import { TriggerFormData, triggerToFormData } from '../../../modules/trigger/form'

export const getServerSideProps: GetServerSideProps<{ trigger: Trigger }> = async (context) => {
  const { id } = context.query
  const trigger = await fetchTriggerByIdWithResponses(String(id))

  if (!trigger) {
    return {
      notFound: true
    }
  }

  return {
    props: {
      trigger: trigger
    }
  }
}

const TriggerEdit = ({ trigger }: InferGetServerSidePropsType<typeof getServerSideProps>) => {
  const router = useRouter()
  const intl = useIntl()

  const closeSidebar = () => {
    setTimeout(async () => await router.push("/"), 500)
  }

  const updateTrigger = async (data: TriggerFormData) => {
    const request = submitTriggerUpdateRequest(data)

    await asyncAlert(request, {
      success: intl.$t({id: 'trigger.form.update.success'}),
      error: intl.$t({ id: 'trigger.form.update.error' }),
      pending: intl.$t({ id: 'trigger.form.update.loading' }),
    })
  }

  return (
    <>
      <GuildTriggersView />
      <TriggerFormSidebar
        title={intl.$t({ id: 'trigger.form.sidebar.edit.title' })}
        initialFormData={triggerToFormData(trigger)}
        onSubmit={updateTrigger}
        onSidebarClose={closeSidebar}
      />
    </>
  )
}

export default TriggerEdit
