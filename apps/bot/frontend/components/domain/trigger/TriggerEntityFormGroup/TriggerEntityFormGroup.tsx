import { TriggerCompare, triggerCompareOptions } from '../../../../shared/domain/trigger'
import { useIntl } from 'react-intl'
import { ForwardedRef, forwardRef, useImperativeHandle, useState } from 'react'
import { Validators } from '../../../../shared/helpers/form/validator'
import { isNotEmpty, isRegex } from '../../../../shared/helpers/validations'
import { useEmitOnChange, useValidator } from '../../../../shared/hooks/form'
import { emptyTriggerFormData, TriggerEntityFormData } from '../../../../shared/helpers/form/trigger/form-data'
import { TriggerFormGroupRef } from '../../../../shared/helpers/form/trigger/handle'
import { FormGroupProps } from '../../../../shared/helpers/form/props'
import TextInput from '../../../common/form/TextInput/TextInput'
import { Option } from '../../../common/form/Select/Select'
import TextSelect from '../../../common/form/TextSelect/TextSelect'

export const TriggerEntityFormGroup = forwardRef(
  (props: FormGroupProps<TriggerEntityFormData>, ref: ForwardedRef<TriggerFormGroupRef>) => {
    const intl = useIntl()
    const [data, setData] = useState<TriggerEntityFormData>(props.data ?? emptyTriggerFormData())

    const validators: Validators = {
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
          skip: () => (data.compare ?? '') != TriggerCompare.PATTERN
        }
      }
    }

    const { errors, cleanErrors, validate, validateSingle } = useValidator(validators, data)
    useImperativeHandle(ref, (): TriggerFormGroupRef => ({ cleanErrors, validate, errors }))
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
          options={triggerCompareOptions().map(([name, value]): Option => ({
            value,
            label: intl.$t({id: `trigger.form.compare.option.${name.toLowerCase()}`})
          }))}
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
