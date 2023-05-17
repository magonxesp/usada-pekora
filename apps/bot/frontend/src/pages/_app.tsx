import '../styles/globals.css'
import 'react-toastify/dist/ReactToastify.css'
import type { AppProps } from 'next/app'
import { SessionProvider } from 'next-auth/react'
import { Session } from 'next-auth'
import { useRouter } from 'next/router'
import { IntlProvider } from 'react-intl'
import es from '../lang/es.json'
import { DefaultLayout } from '@usada-pekora/shared-ui'

interface AppRootProps {
  session: Session,
}

type Translations = {
  [lang: string]: {
    [translation: string]: string
  }
}

const translations: Translations = {
  es
}

function MyApp({ Component, pageProps }: AppProps<AppRootProps>) {
  const router = useRouter()
  const locale = router.locale as string

  return (
    <SessionProvider session={pageProps.session}>
      <IntlProvider locale={locale} messages={translations[locale]}>
        <DefaultLayout>
          <Component {...pageProps} />
        </DefaultLayout>
      </IntlProvider>
    </SessionProvider>
  )
}

export default MyApp
