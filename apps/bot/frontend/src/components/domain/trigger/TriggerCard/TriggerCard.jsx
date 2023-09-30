import Link from 'next/link'
import { useDeleteTrigger } from '../../../../modules/trigger/hooks'
import { Button, Card } from '@usada-pekora/ui/components'
import styles from './TriggerCard.module.css'
import TriggerResponseList from '../TriggerResponseList/TriggerResponseList'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPenToSquare, faTrash } from '@fortawesome/free-solid-svg-icons'

export default function TriggerCard({ trigger }) {
  const deleteTrigger = useDeleteTrigger()

  return (
    <Card className={styles.triggerCard}>
      <>
        <div className={styles.info}>
          <span className={styles.title}>{trigger.title ?? 'Sin título'}</span>
          <TriggerResponseList trigger={trigger} />
        </div>
        <div className={styles.actions}>
          <Link href={`/trigger/edit/${trigger.id}`} >
            <Button style="transparent" className={styles.actionButton}>
              <FontAwesomeIcon icon={faPenToSquare} />
            </Button>
          </Link>
          <Button style="transparent" className={styles.actionButton} onClick={() => deleteTrigger(trigger)}>
            <FontAwesomeIcon className={styles.delete} icon={faTrash} />
          </Button>
        </div>
      </>
    </Card>
  )
}
