import { NextPage } from 'next'
import GuildTriggersView from '../../components/views/GuildTriggersView/GuildTriggersView'
import { useRouter } from 'next/router'
import { useIntl } from 'react-intl'
import TriggerFormSidebar from '../../components/domain/trigger/TriggerFormSidebar/TriggerFormSidebar'
import { useCreateTrigger } from '../../modules/trigger/hooks'
import Head from 'next/head'

const NewTrigger: NextPage = () => {
  const router = useRouter()
  const intl = useIntl()
  const dispatch = useCreateTrigger()

  const closeSidebar = () => {
    setTimeout(async () => await router.push("/"), 500)
  }

  return (
    <>
      <Head>
        <title>{intl.$t({ id: 'trigger.create.page.title' })}</title>
      </Head>
      <GuildTriggersView />
      <TriggerFormSidebar
        title={intl.$t({ id: 'trigger.form.sidebar.create.title' })}
        onSubmit={dispatch}
        onSidebarClose={closeSidebar}
      />
    </>
  )
}

export default NewTrigger
