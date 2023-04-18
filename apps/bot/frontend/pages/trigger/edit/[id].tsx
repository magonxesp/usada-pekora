import { GetServerSideProps, InferGetServerSidePropsType } from 'next'
import GuildTriggersView from '../../../components/views/guild-triggers-view/GuildTriggersView'
import { useRouter } from 'next/router'
import { useIntl } from 'react-intl'
import { asyncAlert } from '../../../shared/helpers/alert'
import TriggerFormSidebar from '../../../components/domain/trigger/form/TriggerFormSidebar'
import { submitTriggerUpdateRequest, TriggerFormData, triggerToFormData } from '../../../shared/helpers/form/trigger/trigger'
import { fetchTriggerById } from '../../../shared/api/backend/trigger'
import { Trigger, TriggerObject } from '../../../shared/domain/trigger'

export const getServerSideProps: GetServerSideProps<{ trigger: TriggerObject }> = async (context) => {
  const { id } = context.query
  const trigger = await fetchTriggerById(id as string)

  if (!trigger) {
    return {
      notFound: true
    }
  }

  const triggerObject = { ...trigger }

  return {
    props: {
      trigger: triggerObject
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
        initialFormData={triggerToFormData(new Trigger(trigger))}
        onSubmit={updateTrigger}
        onSidebarClose={closeSidebar}
      />
    </>
  )
}

export default TriggerEdit
