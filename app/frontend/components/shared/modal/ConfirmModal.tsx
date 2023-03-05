import Modal from './Modal'
import { useIntl } from 'react-intl'
import Button from '../form/Button'
import { closeModal } from '../../../store/slices/app-slice'
import { useDispatch } from 'react-redux'
import { ExclamationCircleIcon } from '@heroicons/react/24/outline'
import styles from './Modal.module.css'

interface ConfirmModalProps {
  onConfirm?: () => void
  onReject?: () => void
  rejectText?: string
  confirmText?: string
  message: string
}

export default function ConfirmModal({ onConfirm, onReject, rejectText, confirmText, message }: ConfirmModalProps) {
  const intl = useIntl()
  const dispatch = useDispatch()

  const handleAction = (action: 'reject' | 'confirm') => {
    dispatch(closeModal())

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
          <div className="flex justify-center text-orange-600">
            <ExclamationCircleIcon className="w-1/4" />
          </div>
          <p className="text-center text-lg py-4">{message}</p>
          <div className="flex justify-center ">
            <Button onClick={() => handleAction('reject')} color="secondary">
              {rejectText ?? intl.$t({ id: 'confirm_modal.default.reject_button_text' })}
            </Button>
            <Button onClick={() => handleAction('confirm')}>
              {confirmText ?? intl.$t({ id: 'confirm_modal.default.confirm_button_text' })}
            </Button>
          </div>
        </>
      </Modal.ModalBody>
    </Modal>
  )
}
