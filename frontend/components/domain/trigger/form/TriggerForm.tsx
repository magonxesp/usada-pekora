import { Trigger, TriggerCompare, triggerCompareOptions } from '../../../../modules/trigger/domain/trigger'
import InputWrapper from '../../../shared/form/input-wrapper/InputWrapper'
import Button from '../../../shared/form/Button'
import Form from '../../../shared/form/Form'
import { ChangeEvent, useState } from 'react'
import { FormErrors, Validator } from '../../../../modules/shared/infraestructure/form/validator'
import { useIntl } from 'react-intl'

interface TriggerFormProps {
  trigger: Trigger,
  onSubmit?: (trigger: Trigger) => void
}

export default function TriggerForm({ trigger }: TriggerFormProps) {
  const [formErrors, setFormErrors] = useState<FormErrors>({})
  const [formData, setFormData] = useState(Trigger.empty().toPrimitives())
  const intl = useIntl()

  const validator = new Validator({
    title: {
      required: {
        validate: (value: string) => value != '',
        errorMessage: intl.$t({ id: 'trigger.form.title.required.error' })
      }
    },
    input: {
      required: {
        validate: (value: string) => value != '',
        errorMessage: intl.$t({ id: 'trigger.form.input.required.error' })
      },
      regex: {
        validate: (value: string) => {
          try {
            if (value == '') {
              return false
            }

            new RegExp(value)
          } catch (exception) {
            return false
          }

          return true
        },
        errorMessage: intl.$t({ id: 'trigger.form.input.regex.error' }),
        skip: () => (formData.compare ?? '') != TriggerCompare.PATTERN
      }
    },
    outputAudio: {
      fileType: {
        validate: (value: string) => true, // TODO: validate audio file type and size
        errorMessage: intl.$t({ id: 'trigger.form.output_audio.file_type.error' })
      }
    }
  })

  const validateInput = (name: string, value: string) => {
    validator.cleanErrorsOf(name)
    validator.validate(name, value)
    const errors = validator.getErrors()

    formErrors[name] = errors[name] ?? []
    setFormErrors(formErrors)
  }

  const handleChangeEvent = (event: ChangeEvent<HTMLInputElement|HTMLSelectElement>) => {
    const name = event.target.name
    const value =  event.target.value

    setFormData({
      ...formData,
      [name]: value
    })

    validateInput(name, value)
  }

  const submitForm = () => {
    validator.cleanErrors()
    validator.validateAll(formData)
    setFormErrors(validator.getErrors())

    if (Object.entries(formErrors).length > 0) {
      console.log('Formulario con errores!')
      return
    }
  }

  return (
    <Form onSubmit={submitForm} className="space-y-4">
      <>
        <InputWrapper label={intl.$t({ id: 'trigger.form.title.label' })}>
          <>
            <InputWrapper.Input>
              <input type="text" defaultValue={trigger.title} onChange={handleChangeEvent} name="title" />
            </InputWrapper.Input>
            {(formErrors.title ?? []).map((error, index) => (
              <InputWrapper.Error key={index}>{error}</InputWrapper.Error>
            ))}
          </>
        </InputWrapper>

        <div className="flex space-x-4">
          <InputWrapper
            label={intl.$t({ id: 'trigger.form.compare.label' })}
            className="w-2/4"
          >
            <InputWrapper.Input>
              <select name="compare" defaultValue={trigger.compare} onChange={handleChangeEvent} >
                {triggerCompareOptions().map(([name, value]) => (
                  <option value={value} key={value}>{name}</option>
                ))}
              </select>
            </InputWrapper.Input>
          </InputWrapper>

          <InputWrapper
            label={intl.$t({ id: 'trigger.form.input.label' })}
            className="w-2/4"
          >
            <>
              <InputWrapper.Input>
                <input type="text" defaultValue={trigger.input} onChange={handleChangeEvent} name="input" />
              </InputWrapper.Input>
              {(formErrors.input ?? []).map((error, index) => (
                <InputWrapper.Error key={index}>{error}</InputWrapper.Error>
              ))}
            </>
          </InputWrapper>
        </div>

        <InputWrapper label={intl.$t({ id: 'trigger.form.output_text.label' })}>
          <InputWrapper.Input>
            <input type="text" defaultValue={trigger.outputText} onChange={handleChangeEvent} name="outputText" />
          </InputWrapper.Input>
        </InputWrapper>

        <InputWrapper label={intl.$t({ id: 'trigger.form.output_audio.label' })}>
          <InputWrapper.Input>
            <input type="file" defaultValue={trigger.outputAudio} accept="audio/mpeg" onChange={handleChangeEvent} name="outputAudio" />
          </InputWrapper.Input>
        </InputWrapper>

        <Button type="submit">{intl.$t({ id: 'trigger.form.submit' })}</Button>
      </>
    </Form>
  )
}
