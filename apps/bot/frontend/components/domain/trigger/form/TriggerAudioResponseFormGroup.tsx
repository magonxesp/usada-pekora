import InputWrapper from '../../../shared/form/input-wrapper/InputWrapper'
import { useIntl } from 'react-intl'
import { ForwardedRef, forwardRef, useEffect, useImperativeHandle } from 'react'
import {
  emptyTriggerResponseAudioFormData,
  TriggerAudioResponseFormData,
} from '../../../../shared/helpers/form/trigger/form-data'
import { TriggerFormGroupRef } from '../../../../shared/helpers/form/trigger/handle'
import { useEmitOnChange, useValidatedFormData } from '../../../../shared/hooks/form'
import { FormGroupProps } from '../../../../shared/helpers/form/props'

export const TriggerAudioResponseFormGroup = forwardRef(
  (props: FormGroupProps<TriggerAudioResponseFormData>, ref: ForwardedRef<TriggerFormGroupRef>) => {
    const intl = useIntl()

    const {
      formData,
      errors,
      validate,
      handleValueChange,
      cleanErrors
    } = useValidatedFormData({}, props.data ?? emptyTriggerResponseAudioFormData())
    useImperativeHandle(ref, (): TriggerFormGroupRef => ({ errors, validate, cleanErrors }))
    useEmitOnChange(props, formData)

    return (
      <InputWrapper
        label={intl.$t({ id: 'trigger.form.output_audio.label' })}
        help={intl.$t({ id: 'trigger.form.output_audio.description' })}
      >
        <>
          <InputWrapper.Input>
            <input
              type="file"
              accept="audio/mpeg"
              onChange={handleValueChange}
              name="responseAudio"
            />
          </InputWrapper.Input>
          {([]).map((error, index) => (
            <InputWrapper.Error key={index}>{error}</InputWrapper.Error>
          ))}
        </>
      </InputWrapper>
    )
  }
)
