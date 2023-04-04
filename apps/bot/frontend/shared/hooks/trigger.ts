import { useDispatch } from 'react-redux'
import { useIntl } from 'react-intl'
import { Trigger } from '../domain/trigger'
import ConfirmModal from '../../components/shared/modal/ConfirmModal'
import { showModal } from '../../store/slices/app-slice'
import { createElement } from 'react'
import { deleteTrigger } from '../api/backend/trigger'
import { asyncAlert } from '../helpers/alert'
import { useFetchTriggers } from './fetch'

export function useDeleteTrigger() {
  const dispatch = useDispatch()
  const intl = useIntl()
  const fetchTriggers = useFetchTriggers()

  return (trigger: Trigger) => {
    const handleDeleteTrigger = () => {
      asyncAlert(deleteTrigger(trigger.id), {
        success: intl.$t({ id: 'trigger.delete.success' }, { title: trigger.title }),
        error: intl.$t({ id: 'trigger.delete.error' }, { title: trigger.title }),
        pending: intl.$t({ id: 'trigger.delete.pending' }, { title: trigger.title }),
      }).then(() => fetchTriggers())
    }

    const modal = createElement(ConfirmModal, {
      message: intl.$t({ id: 'trigger.delete.confirmation.message' }),
      onConfirm: handleDeleteTrigger
    })

    dispatch(showModal(modal))
  }
}
