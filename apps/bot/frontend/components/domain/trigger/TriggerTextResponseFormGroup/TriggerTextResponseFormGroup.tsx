import { useIntl } from 'react-intl'
import { useEmitOnChange, useValidator } from '../../../../shared/hooks/form'
import { Validators } from '../../../../shared/helpers/form/validator'
import { isNotEmpty } from '../../../../shared/helpers/validations'
import { ForwardedRef, forwardRef, useImperativeHandle, useState } from 'react'
import { emptyTriggerResponseTextFormData, TriggerTextResponseFormData } from '../../../../shared/helpers/form/trigger/form-data'
import { TriggerFormGroupRef } from '../../../../shared/helpers/form/trigger/handle'
import { FormGroupProps } from '../../../../shared/helpers/form/props'
import TextInput from '../../../common/form/TextInput/TextInput'

export const TriggerTextResponseFormGroup = forwardRef(
  (props: FormGroupProps<TriggerTextResponseFormData>, ref: ForwardedRef<TriggerFormGroupRef>) => {
    const intl = useIntl()
    const [data, setData] = useState(props.data ?? emptyTriggerResponseTextFormData())

    const validators: Validators = {
      content: {
        required: {
          validate: isNotEmpty,
          errorMessage: intl.$t({ id: 'trigger.form.title.required.error' })
        }
      }
    }

    const { errors, cleanErrors, validate, validateSingle } = useValidator(validators, data)
    useImperativeHandle(ref, (): TriggerFormGroupRef => ({ cleanErrors, validate, errors }))
    useEmitOnChange(props, data)

    return (
      <TextInput
        label={intl.$t({ id: 'trigger.form.response_text.content.label' })}
        help={intl.$t({ id: 'trigger.form.response_text.content.description' })}
        defaultValue={data.content}
        onChange={(value) => {
          setData({ ...data, content: value })
          validateSingle('content', value)
        }}
        error={errors.content}
      />
    )
  }
)
