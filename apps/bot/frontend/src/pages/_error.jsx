import { ErrorView } from '@usada-pekora/ui/components'
import { useIntl } from 'react-intl'

function Error({ statusCode }) {
  const { $t } = useIntl()

  return (
    <ErrorView statusCode={statusCode} message={$t({ id: 'error.internal_server_error' })} />
  )
}

Error.getInitialProps = ({ res, err }) => {
  const statusCode = res ? res.statusCode : err ? err.statusCode : 404
  return { statusCode }
}
 
export default Error
