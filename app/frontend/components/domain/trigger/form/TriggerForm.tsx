import { Trigger, TriggerCompare, triggerCompareOptions } from '../../../../shared/domain/trigger'
import InputWrapper from '../../../shared/form/input-wrapper/InputWrapper'
import Button from '../../../shared/form/Button'
import Form from '../../../shared/form/Form'
import { ChangeEvent, createRef, useState } from 'react'
import { FormErrors, Validator } from '../../../../shared/infraestructure/form/validator'
import { useIntl } from 'react-intl'

interface TriggerFormProps {
  trigger: Trigger,
  onSubmit?: (trigger: Trigger) => void
}

export default function TriggerForm({ trigger, onSubmit }: TriggerFormProps) {
  const [formErrors, setFormErrors] = useState<FormErrors>({})
  const [formData, setFormData] = useState(Trigger.empty().toPrimitives())
  const intl = useIntl()
  const outputAudioRef = createRef<HTMLInputElement>()

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
        validate: () => {
          const file = outputAudioRef.current?.files?.item(0)

          if (!file) {
            return true
          }

          return file.type == 'audio/mpeg'
        },
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
      return
    }

    if (typeof onSubmit !== 'undefined') {
      onSubmit(Trigger.fromPrimitives(formData))
    }
  }

  return (
    <Form onSubmit={submitForm} className="space-y-2.5">
      <>
        <InputWrapper
          label={intl.$t({ id: 'trigger.form.title.label' })}
          help={intl.$t({ id: 'trigger.form.title.description' })}
        >
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
            help={intl.$t({ id: 'trigger.form.compare.description' })}
            className="w-2/4"
          >
            <InputWrapper.Input>
              <select name="compare" defaultValue={trigger.compare} onChange={handleChangeEvent} >
                {triggerCompareOptions().map(([name, value]) => (
                  <option value={value} key={value}>{intl.$t({ id: `trigger.form.compare.option.${name.toLowerCase()}` })}</option>
                ))}
              </select>
            </InputWrapper.Input>
          </InputWrapper>

          <InputWrapper
            label={intl.$t({ id: 'trigger.form.input.label' })}
            help={intl.$t({ id: 'trigger.form.input.description' })}
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

        <InputWrapper
          label={intl.$t({ id: 'trigger.form.output_text.label' })}
          help={intl.$t({ id: 'trigger.form.output_text.description' })}
        >
          <InputWrapper.Input>
            <input type="text" defaultValue={trigger.outputText} onChange={handleChangeEvent} name="outputText" />
          </InputWrapper.Input>
        </InputWrapper>

        <InputWrapper
          label={intl.$t({ id: 'trigger.form.output_audio.label' })}
          help={intl.$t({ id: 'trigger.form.output_audio.description' })}
        >
          <>
            <InputWrapper.Input>
              <input
                type="file"
                defaultValue={trigger.outputAudio}
                accept="audio/mpeg"
                onChange={handleChangeEvent}
                name="outputAudio"
                ref={outputAudioRef}
              />
            </InputWrapper.Input>
            {(formErrors.outputAudio ?? []).map((error, index) => (
              <InputWrapper.Error key={index}>{error}</InputWrapper.Error>
            ))}
          </>
        </InputWrapper>

        <Button type="submit">{intl.$t({ id: 'trigger.form.submit' })}</Button>
      </>
    </Form>
  )
}
