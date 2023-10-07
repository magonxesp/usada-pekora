import { ErrorView } from '@usada-pekora/ui/components'
import { useIntl } from 'react-intl'
import { useRouter } from 'next/navigation'

export default function Custom404() {
  const { $t } = useIntl()
  const router = useRouter()

  return (
    <>
      <ErrorView 
        statusCode={404} 
        message={$t({ id: 'error.not_found' })}
        actionMessage={$t({ id: 'error.not_found.action' })}
        onAction={() => {
          router.push('/')
        }}
      />
    </>
  )
}
