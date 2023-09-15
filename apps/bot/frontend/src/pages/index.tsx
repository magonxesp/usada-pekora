import { NextPage } from 'next'
import GuildTriggersView from '../components/views/GuildTriggersView/GuildTriggersView'
import Head from 'next/head'
import { useIntl } from 'react-intl'

const Home: NextPage = () => {
  const intl = useIntl()

  return (
    <>
      <Head>
        <title>{intl.$t({ id: 'index.page.title' })}</title>
      </Head>
      <GuildTriggersView />
    </>
  )
}

export default Home
