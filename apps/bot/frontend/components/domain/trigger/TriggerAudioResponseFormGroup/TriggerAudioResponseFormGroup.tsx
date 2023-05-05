import { useIntl } from 'react-intl'
import { ForwardedRef, forwardRef, useImperativeHandle, useState } from 'react'
import {
  emptyTriggerResponseAudioFormData,
  TriggerAudioResponseFormData,
} from '../../../../shared/helpers/form/trigger/form-data'
import { TriggerFormGroupRef } from '../../../../shared/helpers/form/trigger/handle'
import { useEmitOnChange, useValidator } from '../../../../shared/hooks/form'
import { FormGroupProps } from '../../../../shared/helpers/form/props'
import FileInput from '../../../common/form/FileInput/FileInput'
import { Validators } from '../../../../shared/helpers/form/validator'

export const TriggerAudioResponseFormGroup = forwardRef(
  (props: FormGroupProps<TriggerAudioResponseFormData>, ref: ForwardedRef<TriggerFormGroupRef>) => {
    const intl = useIntl()
    const [data, setData] = useState(props.data ?? emptyTriggerResponseAudioFormData())

    const validators: Validators = {
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
    useImperativeHandle(ref, (): TriggerFormGroupRef => ({ errors, validate, cleanErrors }))
    useEmitOnChange(props, data)

    return (
      <>
        <FileInput
          label={intl.$t({ id: 'trigger.form.response_audio.file.label' })}
          help={intl.$t({ id: 'trigger.form.response_audio.file.description' })}
          error={errors.content ?? []}
          allowedMimeTypes={['audio/mpeg']}
          onChange={(file) => {
            setData({...data, content: file})
            validateSingle('content', file)
          }}
        />
      </>
    )
  }
)
