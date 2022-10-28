import { Trigger, triggerCompareOptions, TriggerPrimitives } from '../../../../modules/trigger/domain/trigger'
import { ChangeEvent, FormEvent, useReducer } from 'react'

interface TriggerFormProps {
  trigger: Trigger,
  onSubmit?: (trigger: Trigger) => void
}

interface FormDataEvent {
  name: string,
  value: any
}

const formDataReducer = (state: TriggerPrimitives, event: FormDataEvent) => {
  return {
    ...state,
    [event.name]: event.value
  }
}

export default function TriggerForm({ trigger }: TriggerFormProps) {
  const [formData, setFormData] = useReducer(formDataReducer, Trigger.empty().toPrimitives())

  const handleInputChange = (event: ChangeEvent<HTMLInputElement|HTMLSelectElement>) => {
    setFormData({
      name: event.target.name,
      value: event.target.value
    })
  }

  const submitForm = (event: FormEvent) => {
    event.preventDefault()
    console.log(formData)
  }

  return (
    <form onSubmit={submitForm}>
      <input type="text" value={trigger.title} onChange={handleInputChange} name="title" />
      <input type="text" value={trigger.input} onChange={handleInputChange} name="input" />
      <select name="compare" onChange={handleInputChange}>
        {triggerCompareOptions().map(([name, value]) => (
          <option value={value} selected={trigger.compare === value}>{name}</option>
        ))}
      </select>
      <input type="text" value={trigger.outputText} onChange={handleInputChange} name="outputText" />
      <button type="submit">Guardar</button>
    </form>
  )
}
