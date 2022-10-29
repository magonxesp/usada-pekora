import { Trigger, triggerCompareOptions } from '../../../../modules/trigger/domain/trigger'
import InputWrapper from '../../../shared/form/input-wrapper/InputWrapper'
import Button from '../../../shared/form/Button'
import { Form } from '../../../shared/form/Form'

interface TriggerFormProps {
  trigger: Trigger,
  onSubmit?: (trigger: Trigger) => void
}

export default function TriggerForm({ trigger }: TriggerFormProps) {
  const submitForm = (data: object) => {
    console.log(data)
  }

  return (
    <Form initialFormData={trigger.toPrimitives()} onSubmit={submitForm}>
      {(inputChangeHandler) => (
        <>
          <InputWrapper
            label="Titulo"
            onChange={inputChangeHandler}
            validate={(value) => {
              if (!value) {
                throw 'El titulo no puede estar vacio!'
              }
            }}
          >
            {(handler) => (
              <input type="text" defaultValue={trigger.title} onChange={handler}  name="title" />
            )}
          </InputWrapper>

          <InputWrapper
            label="Input"
            onChange={inputChangeHandler}
            validate={(value) => {
              if (!value) {
                throw 'El input no puede estar vacio!'
              }
            }}
          >
            {(handler) => (
              <input type="text" defaultValue={trigger.input} onChange={handler} name="input" />
            )}
          </InputWrapper>

          <InputWrapper
            label="Comparar con:"
            onChange={inputChangeHandler}
          >
            {(handler) => (
              <select name="compare" onChange={handler} defaultValue={trigger.compare}>
                {triggerCompareOptions().map(([name, value]) => (
                  <option value={value} key={value}>{name}</option>
                ))}
              </select>
            )}
          </InputWrapper>

          <InputWrapper
            label="Output text"
            onChange={inputChangeHandler}
          >
            {(handler) => (
              <input type="text" defaultValue={trigger.outputText} onChange={handler} name="outputText" />
            )}
          </InputWrapper>

          <Button type="submit">Guardar</Button>
        </>
      )}
    </Form>
  )
}
