import { useDispatch } from 'react-redux'
import { useIntl } from 'react-intl'
import ConfirmModal from '../../components/common/modal/ConfirmModal/ConfirmModal'
import { showModal } from '../../store/slices/app-slice'
import { createElement } from 'react'
import { alert, asyncAlert } from '../helpers/alert'
import { useFetchTriggers } from './fetch'
import { TriggerFormData } from '../helpers/form/trigger/form-data'
import { createTrigger } from '../api/backend/trigger/create'
import { createTriggerTextResponse } from '../api/backend/trigger/text-response/create-default'
import { deleteTrigger } from '../api/backend/trigger/delete'
import { Trigger } from '../api/backend/trigger/trigger'

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

export function useCreateTrigger() {
  const intl = useIntl()

  const dispatchCreateTrigger = async (data: TriggerFormData): Promise<void> => {
    console.log(data)
    if (data.responseAudio == null && data.responseText == null) {
      alert(intl.$t({ id: 'trigger.form.response.required.error' }), 'error')
      throw new Error('the trigger require at least one response')
    }

    if (data.responseAudio != null) {
      // await createTriggerAudio({
      //   id: uuidv4(),
      //   triggerId: data.id,
      //   guildId: data.discordGuildId,
      //   file: data.responseAudio
      // })
    }

    if (data.responseText != null) {
      await createTriggerTextResponse({
        id: data.responseText.id,
        type: data.responseText.type,
        content: data.responseText.content
      })
    }

    await createTrigger({
      id: data.id,
      title: data.title,
      compare: data.compare,
      input: data.input,
      discordGuildId: data.discordGuildId,
      responseTextId: data.responseText?.id,
      responseAudioId: data.responseAudio?.id,
      responseAudioProvider: data.responseAudio?.provider
    })
  }

  return async (data: TriggerFormData) => {
    const createRequest = dispatchCreateTrigger(data)

    await asyncAlert(createRequest, {
      success: intl.$t({id: 'trigger.form.create.success'}),
      error: intl.$t({ id: 'trigger.form.create.error' }),
      pending: intl.$t({ id: 'trigger.form.create.loading' }),
    })
  }
}
