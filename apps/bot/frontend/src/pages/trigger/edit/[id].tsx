import { GetServerSideProps, InferGetServerSidePropsType } from 'next'
import GuildTriggersView from '../../../components/views/GuildTriggersView/GuildTriggersView'
import { useRouter } from 'next/router'
import { useIntl } from 'react-intl'
import TriggerFormSidebar from '../../../components/domain/trigger/TriggerFormSidebar/TriggerFormSidebar'
import { Trigger } from '../../../modules/trigger/trigger'
import { triggerToFormData } from '../../../modules/trigger/form'
import { useUpdateTrigger } from '../../../modules/trigger/hooks'
import { fetchTriggerByIdWithResponses } from '../../../modules/trigger/api'
import { authorization } from '../../../modules/shared/api'
import Head from 'next/head'

export const getServerSideProps: GetServerSideProps<{ trigger: Trigger }> = async (context) => {
  const { id } = context.query
  console.log(context.req.cookies)
  console.log(context.req.headers.cookie)
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

const TriggerEdit = ({ trigger }: InferGetServerSidePropsType<typeof getServerSideProps>) => {
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
