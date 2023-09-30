import { useIntl } from 'react-intl'
import { useEmitOnChange, useValidator } from '../../../../modules/shared/form/hooks'
import { isNotEmpty } from '../../../../modules/shared/validations'
import { forwardRef, useImperativeHandle, useState } from 'react'
import { TextAreaInput } from '@usada-pekora/ui/components'
import { emptyTriggerResponseTextFormData } from '../../../../modules/trigger/trigger-text-response'

export const TriggerTextResponseFormGroup = forwardRef(
  function TriggerTextResponseFormGroup(props, ref) {
    const intl = useIntl()
    const [data, setData] = useState(props.data ?? emptyTriggerResponseTextFormData())

    const validators = {
      content: {
        required: {
          validate: isNotEmpty,
          errorMessage: intl.$t({ id: 'trigger.form.title.required.error' })
        }
      }
    }

    const { errors, cleanErrors, validate, validateSingle } = useValidator(validators, data)
    useImperativeHandle(ref, () => ({ cleanErrors, validate, errors }))
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
