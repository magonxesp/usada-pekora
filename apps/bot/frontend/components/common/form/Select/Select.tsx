import { useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronDown, faChevronUp } from '@fortawesome/free-solid-svg-icons'

export interface Option {
  label: string
  value: string|number
  disabled?: boolean
}

interface SelectProps {
  options: Option[]
  onChange?: (selected: Option) => void
  selected?: string|number
}

export default function Select({ options, onChange, selected }: SelectProps) {
  const initialSelectedState = options.filter(option => option.value === selected).shift()
  const [selectedOption, setSelectedOption] = useState(initialSelectedState)

  const handleSelectedOption = (option: Option) => {
    if (option.disabled) {
      return
    }

    setSelectedOption(option)
    typeof onChange !== 'undefined' && onChange(option)
  }

  return (
    <div className="select">
      <select>
        {options.map((option, index) => (
          <option
            key={index}
            value={option.value}
            disabled={option.disabled}
            onClick={() => handleSelectedOption(option)}
            selected={selectedOption?.value === option.value}
          >
            {option.label}
          </option>
        ))}
      </select>
      <span className="arrow">
        <FontAwesomeIcon className="arrowIcon" icon={faChevronUp} />
        <FontAwesomeIcon className="arrowIcon" icon={faChevronDown} />
      </span>
    </div>
  )
}

