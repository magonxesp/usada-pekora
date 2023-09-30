import { useIntl } from 'react-intl'
import { forwardRef, useImperativeHandle, useState } from 'react'
import { useEmitOnChange, useValidator } from '../../../../modules/shared/form/hooks'
import { FileInput } from '@usada-pekora/ui/components'
import { TriggerFormGroupRef } from '../../../../modules/trigger/form'
import { emptyTriggerResponseAudioFormData } from '../../../../modules/trigger/trigger-audio-response'

export const TriggerAudioResponseFormGroup = forwardRef(
  function TriggerAudioResponseFormGroup(props, ref) {
    const intl = useIntl()
    const [data, setData] = useState(props.data ?? emptyTriggerResponseAudioFormData())

    const validators = {
      content: {
        required: {
          validate: (file) => file instanceof File,
          errorMessage: intl.$t({ id: 'trigger.form.response_audio.file.required.error' })
        },
        type: {
          validate: (file) => file instanceof File && file.type === 'audio/mpeg',
          errorMessage: intl.$t({ id: 'trigger.form.response_audio.file.file_type.error' })
        },
        size: {
          validate: (file) => file instanceof File && file.size <= (8 * 1024 * 1024),
          errorMessage: intl.$t({ id: 'trigger.form.response_audio.file.file_size.error' })
        }
      }
    }

    const { errors, validate, cleanErrors, validateSingle } = useValidator(validators, data)
    useImperativeHandle(ref, () => ({ errors, validate, cleanErrors }))
    useEmitOnChange(props, data)

    return (
      <>
        <FileInput
          label={intl.$t({ id: 'trigger.form.response_audio.file.label' })}
          help={intl.$t({ id: 'trigger.form.response_audio.file.description' })}
          error={errors.content ?? []}
          allowedMimeTypes={['audio/mpeg']}
          defaultValue={data.content}
          onChange={(file) => {
            setData({...data, content: file})
            validateSingle('content', file)
          }}
          helpText={intl.$t({ id: 'input.file.help.intro' })}
          allowedTypesText={intl.$t({ id: 'input.file.help.allowed_types' })}
          changeText={intl.$t({ id: 'input.file.change' })}
          deleteText={intl.$t({ id: 'input.file.delete' })}
        />
      </>
    )
  }
)
