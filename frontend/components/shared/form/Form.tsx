import { useReducer } from 'react'
import { InputWrapperOnChangeEventHandler } from '../../../modules/shared/infraestructure/react/form/input'

interface InputData {
  name: string
  value: any
}

interface FormProps {
  onSubmit: (data: object) => void
  children: (inputValueHandler: InputWrapperOnChangeEventHandler) => JSX.Element,
  initialFormData: object
}

const formDataReducer = (state: object, event: InputData) => {
  return {
    ...state,
    [event.name]: event.value
  }
}

export function Form({ initialFormData, onSubmit, children }: FormProps) {
  const [formData, setFormData] = useReducer(formDataReducer, initialFormData)

  return (
    <form onSubmit={(event) => {
      event.preventDefault()
      onSubmit(formData)
    }}>
      {children((name, value) => setFormData({ name, value }))}
    </form>
  )
}
