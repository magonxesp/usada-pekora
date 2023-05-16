import { Modal } from '../Modal/Modal'
import { useIntl } from 'react-intl'
import { Button } from '../../form/Button/Button'
import styles from './ConfirmationModal.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCircleExclamation } from '@fortawesome/free-solid-svg-icons'
import { useContext } from 'react'
import { ModalShowContext } from '../ModalContainer/ModalContainer'

interface ConfirmModalProps {
  onConfirm?: () => void
  onReject?: () => void
  rejectText?: string
  confirmText?: string
  message: string
}

export function ConfirmModal({ onConfirm, onReject, rejectText, confirmText, message }: ConfirmModalProps) {
  const intl = useIntl()
  const show = useContext(ModalShowContext)

  const handleAction = (action: 'reject' | 'confirm') => {


    if (action == 'reject' && typeof onReject !== 'undefined') {
      onReject()
    }

    if (action == 'confirm' && typeof onConfirm !== 'undefined') {
      onConfirm()
    }
  }

  return (
    <Modal>
      <Modal.ModalBody className={styles.confirmationModal}>
        <>
          <div className={styles.messageContainer}>
            <FontAwesomeIcon icon={faCircleExclamation} className={styles.icon} />
            <p className={styles.message}>{message}</p>
          </div>
          <div className={styles.actions}>
            <Button onClick={() => handleAction('reject')} style="secondary" className={styles.button}>
              {rejectText ?? intl.$t({ id: 'confirm_modal.default.reject_button_text' })}
            </Button>
            <Button onClick={() => handleAction('confirm')} className={styles.button}>
              {confirmText ?? intl.$t({ id: 'confirm_modal.default.confirm_button_text' })}
            </Button>
          </div>
        </>
      </Modal.ModalBody>
    </Modal>
  )
}
