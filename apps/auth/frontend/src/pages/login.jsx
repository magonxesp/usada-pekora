import { LoginForm } from '../components/LoginForm/LoginForm'
import { Ground } from '../components/Ground/Ground'
import Head from 'next/head'
import { useIntl } from 'react-intl'

export default function Login() {
  const { $t } = useIntl()

  return (
    <>
      <Head>
        <title>{$t({ id: 'title' })}</title>
        <meta name="desription" content={$t({ id: 'description' })} />
        <meta name="robots" content="index, follow" />
      </Head>
      <LoginForm />
      <Ground />
    </>
  )
}
