import GuildTriggersView from '../../../components/views/GuildTriggersView/GuildTriggersView'
import { useRouter } from 'next/router'
import { useIntl } from 'react-intl'
import TriggerFormSidebar from '../../../components/domain/trigger/TriggerFormSidebar/TriggerFormSidebar'
import { triggerToFormData } from '../../../helpers/trigger-form'
import { useUpdateTrigger } from '../../../hooks/trigger'
import { fetchTriggerByIdWithResponses } from '../../../helpers/trigger-api'
import { authorization } from '../../../helpers/api'
import Head from 'next/head'

export const getServerSideProps = async (context) => {
  const { id } = context.query
  const trigger = await fetchTriggerByIdWithResponses(String(id), authorization(context.req) ?? undefined)

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

const TriggerEdit = ({ trigger }) => {
  const router = useRouter()
  const intl = useIntl()
  const dispatch = useUpdateTrigger(trigger)

  const closeSidebar = () => {
    setTimeout(async () => await router.push("/"), 500)
  }

  return (
    <>
      <Head>
        <title>{intl.$t({ id: 'trigger.edit.page.title' })}</title>
      </Head>
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
