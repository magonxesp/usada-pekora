import styles from './InputContainer.module.css'
import InputLabel from '../InputLabel/InputLabel'
import InputError from '../InputError/InputError'

export interface InputContainerProps {
  label: string
  error?: string|string[]
  help?: string
}

interface InputContainerInternalProps extends InputContainerProps {
  children: JSX.Element
}

export default function InputContainer({ label, children, help, error }: InputContainerInternalProps) {
  return (
    <div className={styles.inputContainer}>
      <InputLabel help={help}>{label}</InputLabel>
      {children}
      <InputError>{error ?? []}</InputError>
    </div>
  )
}

