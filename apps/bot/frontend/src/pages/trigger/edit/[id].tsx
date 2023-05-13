import { GetServerSideProps, InferGetServerSidePropsType } from 'next'
import GuildTriggersView from '../../../components/views/GuildTriggersView/GuildTriggersView'
import { useRouter } from 'next/router'
import { useIntl } from 'react-intl'
import TriggerFormSidebar from '../../../components/domain/trigger/TriggerFormSidebar/TriggerFormSidebar'
import { Trigger } from '../../../modules/trigger/trigger'
import { fetchTriggerByIdWithResponses } from '../../../modules/trigger/fetch'
import { triggerToFormData } from '../../../modules/trigger/form'
import { useUpdateTrigger } from '../../../modules/trigger/hooks'

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
  const dispatch = useUpdateTrigger(trigger)

  const closeSidebar = () => {
    setTimeout(async () => await router.push("/"), 500)
  }

  return (
    <>
      <GuildTriggersView />
      <TriggerFormSidebar
        title={intl.$t({ id: 'trigger.form.sidebar.edit.title' })}
        initialFormData={triggerToFormData(trigger)}
        onSubmit={dispatch}
        onSidebarClose={closeSidebar}
      />
    </>
  )
}

export default TriggerEdit
