import InputWrapper from '../../../shared/form/input-wrapper/InputWrapper'
import { useIntl } from 'react-intl'
import { useEmitOnChange, useValidatedFormData } from '../../../../shared/hooks/form'
import { Validators } from '../../../../shared/helpers/form/validator'
import { isNotEmpty } from '../../../../shared/helpers/validations'
import { ForwardedRef, forwardRef, useImperativeHandle } from 'react'
import { emptyTriggerResponseTextFormData, TriggerTextResponseFormData } from '../../../../shared/helpers/form/trigger/form-data'
import { TriggerFormGroupRef } from '../../../../shared/helpers/form/trigger/handle'
import { FormGroupProps } from '../../../../shared/helpers/form/props'

export const TriggerTextResponseFormGroup = forwardRef(
  (props: FormGroupProps<TriggerTextResponseFormData>, ref: ForwardedRef<TriggerFormGroupRef>) => {
    const intl = useIntl()

    const validators: Validators = {
      content: {
        required: {
          validate: isNotEmpty,
          errorMessage: intl.$t({ id: 'trigger.form.title.required.error' })
        }
      }
    }

    const {
      formData,
      errors,
      cleanErrors,
      validate,
      handleValueChange
    } = useValidatedFormData(validators, props.data ?? emptyTriggerResponseTextFormData())
    useImperativeHandle(ref, (): TriggerFormGroupRef => ({ cleanErrors, validate, errors }))
    useEmitOnChange(props, formData)

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
  }
)
