import {FormEvent} from 'react'

interface FormProps {
  onSubmit: () => void
  children: JSX.Element
}

export default function Form({ children, onSubmit }: FormProps) {
  const handleFormSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    onSubmit()
  }

  return (
    <form onSubmit={handleFormSubmit}>
      {children}
    </form>
  )
}
