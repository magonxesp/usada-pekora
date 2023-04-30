import InputContainer, { InputContainerProps } from '../InputContainer/InputContainer'

interface InputProps extends InputContainerProps {
  type: 'text' | 'number'
  onChange?: (value: unknown) => void
  defaultValue?: unknown
}

export default function Input({ label, type, help, error }: InputProps) {
  return (
    <InputContainer
      label={label}
      help={help}
      error={error}
    >
      <input type={type} />
    </InputContainer>
  )
}
