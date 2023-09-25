import { Select } from '../Select/Select'


export function TextSelect({ options, onChange, defaultValue, label, error, help, className }) {
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
      {((option) => (
        <>{option.label}</>
      ))}
    </Select>
  )
}

