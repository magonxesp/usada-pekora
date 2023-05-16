import { Input, InputProps } from '../Input/Input'

interface TextInputProps extends InputProps<string> {}

export function TextInput({ label, help, error, defaultValue, onChange }: TextInputProps) {
  return (
    <Input
      label={label}
      help={help}
      error={error}
    >
      <input
        type="text"
        defaultValue={defaultValue}
        onChange={event => typeof onChange !== 'undefined' && onChange(event.target.value)}
      />
    </Input>
  )
}
