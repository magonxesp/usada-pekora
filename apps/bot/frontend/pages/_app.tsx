import '../styles/globals.css'
import 'react-toastify/dist/ReactToastify.css'
import type { AppProps } from 'next/app'
import Header from '../components/app/header/Header'
import { SessionProvider } from 'next-auth/react'
import { Session } from 'next-auth'
import { Provider } from 'react-redux'
import store from '../store/store'
import { useRouter } from 'next/router'
import { IntlProvider } from 'react-intl'
import es from '../lang/es.json'
import { ToastContainer } from 'react-toastify'
import ModalContainer from '../components/common/modal/ModalContainer/ModalContainer'

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
          <ToastContainer />
          <ModalContainer />
        </IntlProvider>
      </Provider>
    </SessionProvider>
  )
}

export default MyApp
