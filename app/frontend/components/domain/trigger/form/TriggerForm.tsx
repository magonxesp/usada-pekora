import { TriggerCompare, triggerCompareOptions } from '../../../../shared/domain/trigger'
import { TriggerFormData } from '../../../../shared/helpers/form/trigger'
import InputWrapper from '../../../shared/form/input-wrapper/InputWrapper'
import Button from '../../../shared/form/Button'
import Form from '../../../shared/form/Form'
import { ChangeEvent, useEffect, useState } from 'react'
import { FormErrors, Validator } from '../../../../shared/helpers/form/validator'
import { useIntl } from 'react-intl'
import { v4 as uuidv4 } from 'uuid'
import { alert } from '../../../../shared/helpers/alert'
import { useSelectedGuild } from '../../../../shared/hooks/guilds'

interface TriggerFormProps {
  triggerFormData: TriggerFormData,
  onSubmit?: (trigger: TriggerFormData) => void
  disableSubmit?: boolean
}

export default function TriggerForm({ triggerFormData, onSubmit, disableSubmit }: TriggerFormProps) {
  const [formErrors, setFormErrors] = useState<FormErrors>({})
  const [formData, setFormData] = useState(triggerFormData)
  const [id, setId] = useState("")
  const selectedGuild = useSelectedGuild()
  const intl = useIntl()

  useEffect(() => setId(uuidv4()), [])

  const validator = new Validator({
    title: {
      required: {
        validate: (value) => value != '',
        errorMessage: intl.$t({ id: 'trigger.form.title.required.error' })
      }
    },
    input: {
      required: {
        validate: (value) => value != '',
        errorMessage: intl.$t({ id: 'trigger.form.input.required.error' })
      },
      regex: {
        validate: (value) => {
          try {
            if (typeof value != 'string' || value == '') {
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
        validate: (file) => (file instanceof File) ? file.type == 'audio/mpeg' : file == null,
        errorMessage: intl.$t({ id: 'trigger.form.output_audio.file_type.error' })
      }
    }
  })

  const validateInput = (name: string, value: unknown) => {
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

  const handleChangeOutputSound = (event: ChangeEvent<HTMLInputElement>) => {
    const file = (event.target.files != null) ?
      event.target.files[0] : null

    setFormData({
      ...formData,
      outputAudio: file
    })

    validateInput("outputAudio", file)
  }

  const submitForm = () => {
    validator.cleanErrors()
    validator.validateAll(formData)
    const errors = validator.getErrors()
    setFormErrors(errors)

    if (Object.entries(errors).length > 0) {
      return
    }

    if (typeof onSubmit !== 'undefined') {
      if (!selectedGuild) {
        alert(intl.$t({ id: 'trigger.form.error.missing_discord_server_id' }), 'error')
        return
      }

      onSubmit({...formData, uuid: id, discordServerId: selectedGuild})
    }
  }

  return (
    <Form onSubmit={submitForm}>
      <>
        <InputWrapper
          label={intl.$t({ id: 'trigger.form.title.label' })}
          help={intl.$t({ id: 'trigger.form.title.description' })}
        >
          <>
            <InputWrapper.Input>
              <input type="text" defaultValue={triggerFormData.title} onChange={handleChangeEvent} name="title" />
            </InputWrapper.Input>
            {(formErrors.title ?? []).map((error, index) => (
              <InputWrapper.Error key={index}>{error}</InputWrapper.Error>
            ))}
          </>
        </InputWrapper>

        <InputWrapper
          label={intl.$t({ id: 'trigger.form.compare.label' })}
          help={intl.$t({ id: 'trigger.form.compare.description' })}
        >
          <InputWrapper.Input>
            <select name="compare" defaultValue={triggerFormData.compare} onChange={handleChangeEvent} >
              {triggerCompareOptions().map(([name, value]) => (
                <option value={value} key={value}>{intl.$t({ id: `trigger.form.compare.option.${name.toLowerCase()}` })}</option>
              ))}
            </select>
          </InputWrapper.Input>
        </InputWrapper>

        <InputWrapper
          label={intl.$t({ id: 'trigger.form.input.label' })}
          help={intl.$t({ id: 'trigger.form.input.description' })}
        >
          <>
            <InputWrapper.Input>
              <input type="text" defaultValue={triggerFormData.input} onChange={handleChangeEvent} name="input" />
            </InputWrapper.Input>
            {(formErrors.input ?? []).map((error, index) => (
              <InputWrapper.Error key={index}>{error}</InputWrapper.Error>
            ))}
          </>
        </InputWrapper>

        <InputWrapper
          label={intl.$t({ id: 'trigger.form.output_text.label' })}
          help={intl.$t({ id: 'trigger.form.output_text.description' })}
        >
          <InputWrapper.Input>
            <input type="text" defaultValue={triggerFormData.outputText ?? ""} onChange={handleChangeEvent} name="outputText" />
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
                accept="audio/mpeg"
                onChange={handleChangeOutputSound}
                name="outputAudio"
              />
            </InputWrapper.Input>
            {(formErrors.outputAudio ?? []).map((error, index) => (
              <InputWrapper.Error key={index}>{error}</InputWrapper.Error>
            ))}
          </>
        </InputWrapper>

        <Button className="w-full" type="submit" disabled={disableSubmit ?? false}>{intl.$t({ id: 'trigger.form.submit' })}</Button>
      </>
    </Form>
  )
}
