import styles from './Input.module.css'
import { InputLabel } from '../InputLabel/InputLabel'
import { InputError } from '../InputError/InputError'

export function Input({ label, children, help, error, className }) {
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

