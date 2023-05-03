import {FormEvent} from 'react'
import styles from './Form.module.css'

interface FormProps {
  onSubmit: () => void
  children: JSX.Element
  className?: string
}

export default function Form({ children, onSubmit, className }: FormProps) {
  const handleFormSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    onSubmit()
  }

  return (
    <form onSubmit={handleFormSubmit} className={`${styles.form} ${className ?? ''}`}>
      {children}
    </form>
  )
}
