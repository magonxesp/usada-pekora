import { oAuthProviderAuthorize, oAuthProviders } from '../../helpers/backend'
import { Button } from '@usada-pekora/ui/components'
import { useIntl } from 'react-intl'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faDiscord } from '@fortawesome/free-brands-svg-icons'
import styles from './LoginProviderButtons.module.css'

export function LoginProviderButtons() {
  const { $t } = useIntl()

  return (
    <div>
      <Button
        onClick={() => oAuthProviderAuthorize(oAuthProviders.discord)}
        className={styles.button}
      >
        <>
          <FontAwesomeIcon icon={faDiscord} className={styles.icon} />
          {$t({ id: 'login-discord' })}
        </>
      </Button>
    </div>
  )
}
