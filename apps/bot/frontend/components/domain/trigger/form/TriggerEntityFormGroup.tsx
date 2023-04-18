import InputWrapper from '../../../shared/form/input-wrapper/InputWrapper'
import { TriggerCompare, triggerCompareOptions } from '../../../../shared/domain/trigger'
import { useIntl } from 'react-intl'
import { ForwardedRef, forwardRef, useEffect, useImperativeHandle } from 'react'
import { Validators } from '../../../../shared/helpers/form/validator'
import { isNotEmpty, isRegex } from '../../../../shared/helpers/validations'
import { useFormData, useValidator } from '../../../../shared/hooks/form'
import { TriggerEntityFormData } from '../../../../shared/helpers/form/trigger/form-data'
import { TriggerFormGroupRef } from '../../../../shared/helpers/form/trigger/handle'

interface TriggerEntityFormGroupProps {
  data: TriggerEntityFormData
  onChange?: (data: TriggerEntityFormData) => void
}

export const TriggerEntityFormGroup = forwardRef((props: TriggerEntityFormGroupProps, ref: ForwardedRef<TriggerFormGroupRef>) => {
  const intl = useIntl()
  const {formData, handleValueChange} = useFormData(props.data)

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

  const {errors, cleanErrors, validate} = useValidator(validators, formData)
  useImperativeHandle(ref, (): TriggerFormGroupRef => ({cleanErrors, validate, errors}))
  useEffect(() => {
    typeof props.onChange !== 'undefined' && props.onChange(formData)
  }, [formData])

  return (
    <>
      <InputWrapper
        label={intl.$t({id: 'trigger.form.title.label'})}
        help={intl.$t({id: 'trigger.form.title.description'})}
      >
        <>
          <InputWrapper.Input>
            <input type="text" defaultValue={props.data.title} onChange={handleValueChange} name="title"/>
          </InputWrapper.Input>
          {(errors.title ?? []).map((error, index) => (
            <InputWrapper.Error key={index}>{error}</InputWrapper.Error>
          ))}
        </>
      </InputWrapper>

      <InputWrapper
        label={intl.$t({id: 'trigger.form.compare.label'})}
        help={intl.$t({id: 'trigger.form.compare.description'})}
      >
        <InputWrapper.Input>
          <select name="compare" defaultValue={props.data.compare} onChange={handleValueChange}>
            {triggerCompareOptions().map(([name, value]) => (
              <option value={value}
                      key={value}>{intl.$t({id: `trigger.form.compare.option.${name.toLowerCase()}`})}</option>
            ))}
          </select>
        </InputWrapper.Input>
      </InputWrapper>

      <InputWrapper
        label={intl.$t({id: 'trigger.form.input.label'})}
        help={intl.$t({id: 'trigger.form.input.description'})}
      >
        <>
          <InputWrapper.Input>
            <input type="text" defaultValue={props.data.input} onChange={handleValueChange} name="input"/>
          </InputWrapper.Input>
          {(errors.input ?? []).map((error, index) => (
            <InputWrapper.Error key={index}>{error}</InputWrapper.Error>
          ))}
        </>
      </InputWrapper>
    </>
  )
})
