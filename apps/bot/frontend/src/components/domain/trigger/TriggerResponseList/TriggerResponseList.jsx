import styles from './TriggerResponseList.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEnvelope, faMusic } from '@fortawesome/free-solid-svg-icons'
import { useIntl } from 'react-intl'

export default function TriggerResponseList({ trigger }) {
  const intl = useIntl()

  return (
    <div className={styles.triggerResponseList}>
      {(trigger.responseTextId != null) ? (
        <div className={styles.item}>
          <FontAwesomeIcon icon={faEnvelope} />
          <span>{intl.$t({ id: 'trigger.response.text' })}</span>
        </div>
      ) : ''}
      {(trigger.responseAudioId != null) ? (
        <div className={styles.item}>
          <FontAwesomeIcon icon={faMusic} />
          <span>{intl.$t({ id: 'trigger.response.audio' })}</span>
        </div>
      ) : ''}
    </div>
  )
}
