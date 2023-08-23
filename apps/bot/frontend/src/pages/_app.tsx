import '../styles/globals.css'
import 'react-toastify/dist/ReactToastify.css'
import type { AppProps } from 'next/app'
import { useRouter } from 'next/router'
import { IntlProvider } from 'react-intl'
import es from '../lang/es.json'
import { DefaultLayout, UserContext } from '@usada-pekora/shared-ui'
import { useEffect, useState } from 'react'
import { fetchCurrentUser } from '../modules/user/fetch'
import { User } from '@usada-pekora/shared-user'

type Translations = {
  [lang: string]: {
    [translation: string]: string
  }
}

const translations: Translations = {
  es
}

function MyApp({ Component, pageProps }: AppProps) {
  const router = useRouter()
  const locale = router.locale as string
  const [user, setUser] = useState<User|null>(null)

  useEffect(() => {
    fetchCurrentUser().then(user => setUser(user))
  }, [])

  return (
      <IntlProvider locale={locale} messages={translations[locale]}>
        <UserContext.Provider value={user}>
          <DefaultLayout>
            <Component {...pageProps} />
          </DefaultLayout>
        </UserContext.Provider>
      </IntlProvider>
  )
}

export default MyApp
