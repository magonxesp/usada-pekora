import { useIntl } from 'react-intl'
import { ConfirmModal, useModal } from '@usada-pekora/shared-ui'
import { createElement } from 'react'
import { alert, asyncAlert } from '../shared/alert'
import { createTrigger } from './create'
import { createTriggerTextResponse } from './text-response/create-default'
import { deleteTrigger } from './delete'
import { Trigger } from './trigger'
import { createTriggerAudio } from './audio-response/create-default'
import { TriggerFormData } from './form'
import { useSelectedGuild } from '../guild/hooks'
import { fetchGuildTriggers } from './fetch'
import { updateTrigger } from './update'
import { updateTriggerTextResponse } from './text-response/update-default'
import { deleteTriggerTextResponse } from './text-response/delete-default'
import { deleteTriggerDefaultAudioResponse } from './audio-response/delete-default'
import { updateTriggerDefaultAudioResponse } from './audio-response/update-default'
import { useAppStore } from '../../store/app'

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

    show(modal)
  }
}

export function useCreateTrigger() {
  const intl = useIntl()

  const dispatchCreateTrigger = async (data: TriggerFormData): Promise<void> => {
    if (data.responseAudio == null && data.responseText == null) {
      alert(intl.$t({ id: 'trigger.form.response.required.error' }), 'error')
      throw new Error('the trigger require at least one response')
    }

    if (data.responseAudio != null) {
      await createTriggerAudio({
        id: data.responseAudio.id,
        triggerId: data.id,
        guildId: data.discordGuildId,
        file: data.responseAudio.content as File
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

export function useUpdateTrigger(actualTrigger: Trigger) {
  const intl = useIntl()

  const dispatchUpdateTrigger = async (data: TriggerFormData) => {
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
      await updateTriggerTextResponse({
        id: data.responseText.id,
        values: {
          content: data.responseText.content,
          type: data.responseText.type,
        }
      })
    } else if (!data.responseText && actualTrigger.responseTextId != null) {
      await deleteTriggerTextResponse(actualTrigger.responseTextId)
    }

    if (data.responseAudio != null && !actualTrigger.responseAudioId) {
      await createTriggerAudio({
        id: data.responseAudio.id,
        triggerId: data.id,
        guildId: data.discordGuildId,
        file: data.responseAudio.content as File
      })
    } else if (data.responseAudio != null && actualTrigger.responseAudioId != null) {
      await updateTriggerDefaultAudioResponse({
        id: data.responseAudio.id,
        values: {
          triggerId: data.id,
          guildId: data.discordGuildId,
          file: data.responseAudio.content ?? undefined,
        },
      })
    } if (!data.responseAudio && actualTrigger.responseAudioId != null) {
      await deleteTriggerDefaultAudioResponse(actualTrigger.responseAudioId)
    }

    await updateTrigger({
      id: data.id,
      values: {
        title: data.title,
        compare: data.compare,
        input: data.input,
        discordGuildId: data.discordGuildId,
        responseTextId: data.responseText?.id,
        responseAudioId: data.responseAudio?.id,
        responseAudioProvider: data.responseAudio?.provider
      }
    })
  }

  return async (data: TriggerFormData) => {
    const request = dispatchUpdateTrigger(data)

    await asyncAlert(request, {
      success: intl.$t({id: 'trigger.form.update.success'}),
      error: intl.$t({ id: 'trigger.form.update.error' }),
      pending: intl.$t({ id: 'trigger.form.update.loading' }),
    })
  }
}
