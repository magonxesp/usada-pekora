import { Select, SelectOption, SelectProps } from '../Select/Select'

interface TextSelectProps extends SelectProps<SelectOption> {
  options: SelectOption[]
}

export function TextSelect({ options, onChange, defaultValue, label, error, help, className }: TextSelectProps) {
  return (
    <Select
      options={options}
      label={label}
      help={help}
      error={error}
      className={className}
      onChange={onChange}
      defaultValue={defaultValue}
    >
      {((option: SelectOption) => (
        <>{option.label}</>
      ))}
    </Select>
  )
}

