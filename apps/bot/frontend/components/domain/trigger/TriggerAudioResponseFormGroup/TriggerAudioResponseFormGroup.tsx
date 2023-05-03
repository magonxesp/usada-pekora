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

export const TriggerAudioResponseFormGroup = forwardRef(
  (props: FormGroupProps<TriggerAudioResponseFormData>, ref: ForwardedRef<TriggerFormGroupRef>) => {
    const intl = useIntl()
    const [data, setData] = useState(props.data ?? emptyTriggerResponseAudioFormData())

    const { errors, validate, cleanErrors, validateSingle } = useValidator({}, data)
    useImperativeHandle(ref, (): TriggerFormGroupRef => ({ errors, validate, cleanErrors }))
    useEmitOnChange(props, data)

    return (
      <>
        <FileInput
          label={intl.$t({ id: 'trigger.form.response_audio.file.label' })}
          help={intl.$t({ id: 'trigger.form.response_audio.file.description' })}
          allowedMimeTypes={['audio/mpeg']}
        />
      </>
    )
  }
)
