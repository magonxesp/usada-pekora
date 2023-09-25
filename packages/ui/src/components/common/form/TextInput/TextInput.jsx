import { Input } from '../Input/Input'

export function TextInput({ label, help, error, defaultValue, onChange }) {
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
