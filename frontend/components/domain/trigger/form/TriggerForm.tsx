import { Trigger, triggerCompareOptions } from '../../../../modules/trigger/domain/trigger'
import InputWrapper from '../../../shared/form/input-wrapper/InputWrapper'
import Button from '../../../shared/form/Button'
import Form from '../../../shared/form/Form'
import { ChangeEvent, useState } from 'react'

interface TriggerFormProps {
  trigger: Trigger,
  onSubmit?: (trigger: Trigger) => void
}

type ValidValues = { [name: string]: boolean }
type Validators = { [name: string]: (value: string) => boolean }

export default function TriggerForm({ trigger }: TriggerFormProps) {
  const [validatedValues, setValidatedValues] = useState<ValidValues>({})
  const [formData, setFormData] = useState(Trigger.empty().toPrimitives())

  const validators: Validators = {
    title: (value: string) => {
      return value != ''
    },
    input: (value: string) => {
      return value != ''
    }
  }

  const hasError = (name: string) => {
    const _hasError = Object.entries(validatedValues)
      .filter(([_name, isValid]) => _name == name && !isValid)
      .length > 0

    console.log(_hasError)
    return _hasError
  }

  const validate = (name: string, value: string) => {
    const hasValidator = Object.entries(validators)
      .map(([name]) => name)
      .includes(name)

    if (hasValidator) {
      const validator = validators[name]
      validatedValues[name] = validator(value)
      setValidatedValues(validatedValues)
    }
  }

  const handleChangeEvent = (event: ChangeEvent<HTMLInputElement|HTMLSelectElement>) => {
    const name = event.target.name
    const value =  event.target.value

    setFormData({
      ...formData,
      [name]: value
    })

    validate(name, value)
  }

  const validateFormData = () => {
    Object.entries(formData).forEach(([name, value]) => {
      validate(name, value)
    })
  }

  const submitForm = () => {
    setValidatedValues({})
    validateFormData()
  }

  return (
    <Form onSubmit={submitForm}>
      <>
        <InputWrapper label="Titulo">
          <InputWrapper.Input>
            <input type="text" defaultValue={trigger.title} onChange={handleChangeEvent} name="title" />
          </InputWrapper.Input>
          <InputWrapper.Error>
            {(hasError('title')) ? 'El titulo es obligatorio' : ''}
          </InputWrapper.Error>
        </InputWrapper>

        <InputWrapper label="Input">
          <InputWrapper.Input>
            <input type="text" defaultValue={trigger.input} onChange={handleChangeEvent} name="input" />
          </InputWrapper.Input>
          <InputWrapper.Error>
            {(hasError('input')) ? 'El input es obligatorio' : ''}
          </InputWrapper.Error>
        </InputWrapper>

        <InputWrapper label="Comparar con:">
          <InputWrapper.Input>
            <select name="compare" defaultValue={trigger.compare} onChange={handleChangeEvent} >
              {triggerCompareOptions().map(([name, value]) => (
                <option value={value} key={value}>{name}</option>
              ))}
            </select>
          </InputWrapper.Input>
          <></>
        </InputWrapper>

        <InputWrapper label="Output text">
          <InputWrapper.Input>
            <input type="text" defaultValue={trigger.outputText} onChange={handleChangeEvent} name="outputText" />
          </InputWrapper.Input>
          <></>
        </InputWrapper>

        <Button
          type="submit"
          disabled={Object.entries(validatedValues).filter(([name, isValid]) => !isValid).length > 0}
        >Guardar</Button>
      </>
    </Form>
  )
}
