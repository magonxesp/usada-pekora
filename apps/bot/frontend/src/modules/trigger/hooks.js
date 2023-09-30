import { useIntl } from 'react-intl'
import { ConfirmModal } from '@usada-pekora/ui/components'
import { useModal } from '@usada-pekora/ui/hooks'
import { createElement } from 'react'
import { alert, asyncAlert } from '../shared/alert'
import { useSelectedGuild } from '../guild/hooks'
import { useAppStore } from '../../store/app'
import {
  createTrigger,
  createTriggerAudio,
  createTriggerTextResponse,
  deleteTrigger,
  deleteTriggerDefaultAudioResponse,
  deleteTriggerTextResponse,
  fetchGuildTriggers,
  updateTrigger,
  updateTriggerDefaultAudioResponse,
  updateTriggerTextResponse,
} from './api'

export function useFetchTriggers() {
  const { selected } = useSelectedGuild()
  const setTriggers = useAppStore(state => state.setTriggers)

  return async () => {
    const triggers = await fetchGuildTriggers(selected)
    setTriggers(triggers)
  }
}

export function useGetTriggers() {
  return useAppStore(state => state.triggers)
}

export function useDeleteTrigger() {
  const intl = useIntl()
  const fetchTriggers = useFetchTriggers()
  const { show } = useModal()

  return (trigger) => {
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

    show(modal)
  }
}

export function useCreateTrigger() {
  const intl = useIntl()

  const dispatchCreateTrigger = async (data) => {
    if (data.responseAudio == null && data.responseText == null) {
      alert(intl.$t({ id: 'trigger.form.response.required.error' }), 'error')
      throw new Error('the trigger require at least one response')
    }

    if (data.responseAudio != null) {
      await createTriggerAudio({
        id: data.responseAudio.id,
        triggerId: data.id,
        guildId: data.guildId,
        file: data.responseAudio.content
      })
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
      guildId: data.guildId,
      responseTextId: data.responseText?.id,
      responseAudioId: data.responseAudio?.id,
    })
  }

  return async (data) => {
    const createRequest = dispatchCreateTrigger(data)

    await asyncAlert(createRequest, {
      success: intl.$t({id: 'trigger.form.create.success'}),
      error: intl.$t({ id: 'trigger.form.create.error' }),
      pending: intl.$t({ id: 'trigger.form.create.loading' }),
    })
  }
}

export function useUpdateTrigger(actualTrigger) {
  const intl = useIntl()

  const dispatchUpdateTrigger = async (data) => {
    if (data.responseAudio == null && data.responseText == null) {
      alert(intl.$t({id: 'trigger.form.response.required.error'}), 'error')
      throw new Error('the trigger require at least one response')
    }

    if (data.responseText != null && !actualTrigger.responseTextId) {
      await createTriggerTextResponse({
        id: data.responseText.id,
        type: data.responseText.type,
        content: data.responseText.content
      })
    } else if (data.responseText != null && actualTrigger.responseTextId != null) {
      await updateTriggerTextResponse(data.responseText.id, {
        content: data.responseText.content,
        type: data.responseText.type,
      })
    } else if (!data.responseText && actualTrigger.responseTextId != null) {
      await deleteTriggerTextResponse(actualTrigger.responseTextId)
    }

    if (data.responseAudio != null 
      && data.responseAudio.content instanceof File
      && data.responseAudio.content.size > 0
      && !actualTrigger.responseAudioId
    ) {
      await createTriggerAudio({
        id: data.responseAudio.id,
        triggerId: data.id,
        guildId: data.guildId,
        file: data.responseAudio.content
      })
    } else if (data.responseAudio != null 
      && data.responseAudio.content instanceof File
      && data.responseAudio.content.size > 0
      && actualTrigger.responseAudioId != null
    ) {
      await updateTriggerDefaultAudioResponse(data.responseAudio.id, {
        triggerId: data.id,
        guildId: data.guildId,
        file: data.responseAudio.content,
      })
    } if (!data.responseAudio && actualTrigger.responseAudioId != null) {
      await deleteTriggerDefaultAudioResponse(actualTrigger.responseAudioId)
    }

    await updateTrigger(data.id, {
      title: data.title,
      compare: data.compare,
      input: data.input,
      guildId: data.guildId,
      responseTextId: data.responseText?.id,
      responseAudioId: data.responseAudio?.id,
    })
  }

  return async (data) => {
    const request = dispatchUpdateTrigger(data)

    await asyncAlert(request, {
      success: intl.$t({id: 'trigger.form.update.success'}),
      error: intl.$t({ id: 'trigger.form.update.error' }),
      pending: intl.$t({ id: 'trigger.form.update.loading' }),
    })
  }
}
