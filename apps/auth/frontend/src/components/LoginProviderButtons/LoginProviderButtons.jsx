import { oAuthProviderAuthorize, oAuthProviders } from '../../helpers/backend'
import { Button } from '@usada-pekora/ui/components'
import useTranslation from 'next-translate/useTranslation'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faDiscord } from '@fortawesome/free-brands-svg-icons'
import styles from './LoginProviderButtons.module.css'

export function LoginProviderButtons() {
  const { t } = useTranslation('login')

  return (
    <div>
      <Button
        onClick={() => oAuthProviderAuthorize(oAuthProviders.discord)}
        className={styles.button}
      >
        <>
          <FontAwesomeIcon icon={faDiscord} className={styles.icon} />
          {t('login-discord')}
        </>
      </Button>
    </div>
  )
}
