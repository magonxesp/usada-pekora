import '../styles/globals.css'
import type { AppProps } from 'next/app'
import Header from '../components/app/header/Header'
import { SessionProvider } from 'next-auth/react'
import { Session } from 'next-auth'
import { Provider } from 'react-redux'
import store from '../store/store'
import { useRouter } from 'next/router'
import { IntlProvider } from 'react-intl'
import es from '../lang/es.json'

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
      <Provider store={store}>
        <IntlProvider locale={locale} messages={translations[locale]}>
          <Header />
          <main className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
            <Component {...pageProps} />
          </main>
        </IntlProvider>
      </Provider>
    </SessionProvider>
  )
}

export default MyApp