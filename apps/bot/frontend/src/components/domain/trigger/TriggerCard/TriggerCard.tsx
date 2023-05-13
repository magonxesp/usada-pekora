import Button from '../../../common/form/Button/Button'
import Link from 'next/link'
import { useDeleteTrigger } from '../../../../modules/trigger/hooks'
import { Trigger } from '../../../../modules/trigger/trigger'
import Card from '../../../common/layout/Card/Card'
import styles from './TriggerCard.module.css'
import TriggerResponseList from '../TriggerResponseList/TriggerResponseList'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPenToSquare, faTrash } from '@fortawesome/free-solid-svg-icons'

interface TriggerCardProps {
  trigger: Trigger
}

export default function TriggerCard({ trigger }: TriggerCardProps) {
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
            <Button style="transparent">
              <FontAwesomeIcon icon={faPenToSquare} />
            </Button>
          </Link>
          <Button style="transparent" onClick={() => deleteTrigger(trigger)}>
            <FontAwesomeIcon className={styles.delete} icon={faTrash} />
          </Button>
        </div>
      </>
    </Card>
  )
}
