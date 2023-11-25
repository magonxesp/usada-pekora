import { useAppStore } from '../../../store/app'
import { ErrorView } from '@usada-pekora/ui/components'
import { useIntl } from 'react-intl'

export default function ErrorAwareView({ children }) {
  const error = useAppStore(state => state.error)
  const { $t } = useIntl()

  return (
    <>
      {error ? (
        <ErrorView 
          statusCode={500} 
          message={$t({ id: 'error.internal_server_error' })}
          actionMessage={$t({ id: 'error.internal_server_error.action' })}
          onAction={() => window.location.reload()}
        />
      ) : children}
    </>
  )
}
