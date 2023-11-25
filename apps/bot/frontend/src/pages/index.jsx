import GuildTriggersView from '../components/views/GuildTriggersView/GuildTriggersView'
import Head from 'next/head'
import { useIntl } from 'react-intl'
import ErrorAwareView from '../components/views/ErrorAwareView/ErrorAwareView'

const Home = () => {
  const intl = useIntl()

  return (
    <>
      <Head>
        <title>{intl.$t({ id: 'index.page.title' })}</title>
      </Head>
      <ErrorAwareView>
        <GuildTriggersView />
      </ErrorAwareView>
    </>
  )
}

export default Home
