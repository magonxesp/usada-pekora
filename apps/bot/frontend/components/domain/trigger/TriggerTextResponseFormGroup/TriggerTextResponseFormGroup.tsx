import { useIntl } from 'react-intl'
import { useEmitOnChange, useValidator } from '../../../../modules/shared/form/hooks'
import { Validators } from '../../../../modules/shared/form/validator'
import { isNotEmpty } from '../../../../modules/shared/validations'
import { ForwardedRef, forwardRef, useImperativeHandle, useState } from 'react'
import { FormGroupProps } from '../../../../modules/shared/form/props'
import TextInput from '../../../common/form/TextInput/TextInput'
import TextAreaInput from '../../../common/form/TextAreaInput/TextAreaInput'
import { TriggerFormGroupRef } from '../../../../modules/trigger/form'
import {
  emptyTriggerResponseTextFormData,
  TriggerTextResponseFormData
} from '../../../../modules/trigger/text-response/form'

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
      <TextAreaInput
        label={intl.$t({ id: 'trigger.form.response_text.content.label' })}
        help={intl.$t({ id: 'trigger.form.response_text.content.description' })}
        defaultValue={data.content}
        onChange={(value) => {
          setData({ ...data, content: value })
          validateSingle('content', value)
        }}
        error={errors.content}
        limit={255}
      />
    )
  }
)
