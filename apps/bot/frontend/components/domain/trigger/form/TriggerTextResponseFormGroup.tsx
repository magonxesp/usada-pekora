import InputWrapper from '../../../shared/form/input-wrapper/InputWrapper'
import { useIntl } from 'react-intl'
import { useFormData, useValidator } from '../../../../shared/hooks/form'
import { FormErrors, Validators } from '../../../../shared/helpers/form/validator'
import { isNotEmpty, isRegex } from '../../../../shared/helpers/validations'
import { ForwardedRef, forwardRef, useEffect, useImperativeHandle } from 'react'
import { emptyTriggerResponseTextFormData, TriggerTextResponseFormData } from '../../../../shared/helpers/form/trigger/form-data'
import { TriggerFormGroupRef } from '../../../../shared/helpers/form/trigger/handle'

export interface TriggerTextResponseFormGroupProps {
  data?: TriggerTextResponseFormData
  onChange?: (data: TriggerTextResponseFormData) => void
}

export const TriggerTextResponseFormGroup = forwardRef((props: TriggerTextResponseFormGroupProps, ref: ForwardedRef<TriggerFormGroupRef>) => {
  const intl = useIntl()
  const { formData, handleValueChange } = useFormData(props.data ?? emptyTriggerResponseTextFormData())

  const validators: Validators = {
    content: {
      required: {
        validate: isNotEmpty,
        errorMessage: intl.$t({ id: 'trigger.form.title.required.error' })
      }
    }
  }

  const { errors, cleanErrors, validate } = useValidator(validators, formData)

  useImperativeHandle(ref, (): TriggerFormGroupRef => ({ cleanErrors, validate, errors }))
  useEffect(() => {
    typeof props.onChange !== 'undefined' && props.onChange(formData)
  }, [formData])

  return (
    <InputWrapper
      label={intl.$t({ id: 'trigger.form.output_text.label' })}
      help={intl.$t({ id: 'trigger.form.output_text.description' })}
    >
      <InputWrapper.Input>
        <input type="text" defaultValue={formData.content} onChange={handleValueChange} name="content" />
      </InputWrapper.Input>
    </InputWrapper>
  )
})
