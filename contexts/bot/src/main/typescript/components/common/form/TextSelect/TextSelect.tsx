import Select, { Option, SelectProps } from '../Select/Select'

interface TextSelectProps extends SelectProps<Option> {
  options: Option[]
}

export default function TextSelect({ options, onChange, defaultValue, label, error, help, className }: TextSelectProps) {
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
      {((option: Option) => (
        <>{option.label}</>
      ))}
    </Select>
  )
}

