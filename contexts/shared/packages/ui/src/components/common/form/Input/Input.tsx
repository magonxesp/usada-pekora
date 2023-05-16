import styles from './Input.module.css'
import { InputLabel } from '../InputLabel/InputLabel'
import { InputError } from '../InputError/InputError'

export interface InputProps<T> {
  label?: string
  error?: string|string[]
  help?: string
  onChange?: (value: T) => void
  defaultValue?: T
  className?: string
}

interface InputContainerInternalProps extends InputProps<unknown> {
  children: JSX.Element
}

export function Input({ label, children, help, error, className }: InputContainerInternalProps) {
  return (
    <div className={`${styles.input} ${className ?? ''}`}>
      {(label) ? (
        <InputLabel help={help}>{label}</InputLabel>
      ) : ''}
      {children}
      <InputError>{error ?? []}</InputError>
    </div>
  )
}

