import { useIntl } from 'react-intl'
import { forwardRef, useImperativeHandle, useState } from 'react'
import { isNotEmpty, isRegex } from '../../../../modules/shared/validations'
import { useEmitOnChange, useValidator } from '../../../../modules/shared/form/hooks'
import { TextInput, TextSelect } from '@usada-pekora/ui/components'
import { triggerCompare } from '../../../../modules/trigger/trigger'
import { emptyTriggerFormData } from '../../../../modules/trigger/form'

export const TriggerEntityFormGroup = forwardRef(
  function TriggerEntityFormGroup(props, ref) {
    const intl = useIntl()
    const [data, setData] = useState(props.data ?? emptyTriggerFormData())

    const validators = {
      title: {
        required: {
          validate: isNotEmpty,
          errorMessage: intl.$t({id: 'trigger.form.title.required.error'})
        }
      },
      input: {
        required: {
          validate: isNotEmpty,
          errorMessage: intl.$t({id: 'trigger.form.input.required.error'})
        },
        regex: {
          validate: isRegex,
          errorMessage: intl.$t({id: 'trigger.form.input.regex.error'}),
          skip: () => (data.compare ?? '') != triggerCompare.pattern
        }
      }
    }

    const { errors, cleanErrors, validate, validateSingle } = useValidator(validators, data)
    useImperativeHandle(ref, () => ({ cleanErrors, validate, errors }))
    useEmitOnChange(props, data)

    return (
      <>
        <TextInput
          label={intl.$t({id: 'trigger.form.title.label'})}
          help={intl.$t({id: 'trigger.form.title.description'})}
          error={errors.title ?? []}
          defaultValue={props.data?.title}
          onChange={(value) => {
            setData({...data, title: value})
            validateSingle('title', value)
          }}
        />

        <TextSelect
          label={intl.$t({id: 'trigger.form.compare.label'})}
          help={intl.$t({id: 'trigger.form.compare.description'})}
          options={Object.entries(triggerCompare).map(([name, value]) => ({
            value,
            label: intl.$t({id: `trigger.form.compare.option.${name}`})
          }))}
          defaultValue={props.data?.compare}
          onChange={(value) => {
            setData({...data, compare: String(value)})
            validateSingle('compare', value)
          }}
        />

        <TextInput
          label={intl.$t({id: 'trigger.form.input.label'})}
          help={intl.$t({id: 'trigger.form.input.description'})}
          error={errors.input ?? []}
          defaultValue={props.data?.input}
          onChange={(value) => {
            setData({...data, input: value})
            validateSingle('input', value)
          }}
        />
      </>
    )
  }
)
