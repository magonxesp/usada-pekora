import '../styles/globals.css'
import { useRouter } from 'next/router'
import { IntlProvider } from 'react-intl'
import es from '../locales/es.json'

const translations = {
  es
}

export default function MyApp({ Component, pageProps }) {
  const router = useRouter()
  const locale = router.locale

  return (
    <IntlProvider locale={locale} messages={translations[locale]}>
      <main>
        <Component {...pageProps} />
      </main>
    </IntlProvider>
  )
}
