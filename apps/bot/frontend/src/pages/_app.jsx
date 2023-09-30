import '../styles/globals.css'
import 'react-toastify/dist/ReactToastify.css'
import { useRouter } from 'next/router'
import { IntlProvider } from 'react-intl'
import es from '../lang/es.json'
import { DefaultLayout } from '@usada-pekora/ui/components'
import { UserContext } from '@usada-pekora/ui/helpers'
import { useEffect, useState } from 'react'
import { fetchCurrentUser } from '../modules/user/fetch'

const translations = {
  es
}

function MyApp({ Component, pageProps }) {
  const router = useRouter()
  const locale = router.locale
  const [user, setUser] = useState(null)

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
