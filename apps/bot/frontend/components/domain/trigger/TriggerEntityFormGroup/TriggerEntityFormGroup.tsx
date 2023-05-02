import InputWrapper from '../../../common/form/InputWrapper/InputWrapper'
import { TriggerCompare, triggerCompareOptions } from '../../../../shared/domain/trigger'
import { useIntl } from 'react-intl'
import { ForwardedRef, forwardRef, useEffect, useImperativeHandle } from 'react'
import { Validators } from '../../../../shared/helpers/form/validator'
import { isNotEmpty, isRegex } from '../../../../shared/helpers/validations'
import { useEmitOnChange, useFormData, useValidatedFormData, useValidator } from '../../../../shared/hooks/form'
import { TriggerEntityFormData } from '../../../../shared/helpers/form/trigger/form-data'
import { TriggerFormGroupRef } from '../../../../shared/helpers/form/trigger/handle'
import { FormGroupProps } from '../../../../shared/helpers/form/props'
import TextInput from '../../../common/form/TextInput/TextInput'
import Select, { Option } from '../../../common/form/Select/Select'
import TextSelect from '../../../common/form/TextSelect/TextSelect'

export const TriggerEntityFormGroup = forwardRef(
  (props: FormGroupProps<TriggerEntityFormData>, ref: ForwardedRef<TriggerFormGroupRef>) => {
    const intl = useIntl()

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
          skip: () => (formData.compare ?? '') != TriggerCompare.PATTERN
        }
      }
    }

    const {
      formData,
      errors,
      cleanErrors,
      validate,
      handleValueChange
    } = useValidatedFormData(validators, props.data as TriggerEntityFormData)
    useImperativeHandle(ref, (): TriggerFormGroupRef => ({ cleanErrors, validate, errors }))
    useEmitOnChange(props, formData)

    return (
      <>
        <TextInput
          label={intl.$t({id: 'trigger.form.title.label'})}
          help={intl.$t({id: 'trigger.form.title.description'})}
          error={errors.title ?? []}
          defaultValue={props.data?.title}
        />

        <TextSelect
          label={intl.$t({id: 'trigger.form.compare.label'})}
          help={intl.$t({id: 'trigger.form.compare.description'})}
          options={triggerCompareOptions().map(([name, value]): Option => ({
            value,
            label: intl.$t({id: `trigger.form.compare.option.${name.toLowerCase()}`})
          }))}
        />

        <TextInput
          label={intl.$t({id: 'trigger.form.input.label'})}
          help={intl.$t({id: 'trigger.form.input.description'})}
          error={errors.title ?? []}
          defaultValue={props.data?.input}
        />
      </>
    )
  }
)
