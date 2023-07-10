import { LoginForm } from '../components/LoginForm/LoginForm'
import { Ground } from '../components/Ground/Ground'
import Head from 'next/head'
import useTranslation from 'next-translate/useTranslation'

export default function Login() {
  const { t } = useTranslation('login')

  return (
    <>
      <Head>
        <title>{t('title')}</title>
      </Head>
      <LoginForm />
      <Ground />
    </>
  )
}
