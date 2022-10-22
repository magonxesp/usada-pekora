import '../styles/globals.css'
import type { AppProps } from 'next/app'
import Header from '../components/app/header/Header'
import { SessionProvider } from 'next-auth/react'
import { Session } from 'next-auth'
import { Provider } from 'react-redux'
import store from '../store/store'

interface AppRootProps {
  session: Session,
}

function MyApp({ Component, pageProps }: AppProps<AppRootProps>) {
  return (
    <SessionProvider session={pageProps.session}>
      <Provider store={store}>
        <Header />
        <main>
          <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
            <Component {...pageProps} />
          </div>
        </main>
      </Provider>
    </SessionProvider>
  )
}

export default MyApp
