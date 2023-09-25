import { useEffect, useRef, useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronDown, faChevronUp } from '@fortawesome/free-solid-svg-icons'
import { Input } from '../Input/Input'
import styles from './Select.module.css'

function SelectOption({ option, onClick, children, className }) {
  return (
    <div className={`${styles.option} ${className ?? ''}`} onClick={() => typeof onClick !== 'undefined' && onClick(option)}>
      {(typeof children !== 'undefined') ? children(option) : ''}
    </div>
  )
}

export function Select({
  options,
  onChange,
  defaultValue,
  label,
  error,
  help,
  className,
  optionClassName,
  children
}) {
  const defaultOption = {
    label: "-",
    value: ""
  }

  const firstOptionValue = options[0]?.value
  const initialSelectedState = options.filter(option => option.value === (defaultValue ?? firstOptionValue)).shift()
  const [selectedOption, setSelectedOption] = useState(initialSelectedState)
  const [showOptions, setShowOptions] = useState(false)
  const selectRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    document.addEventListener('mousedown', (event) => {
      if (event.target instanceof HTMLElement && selectRef.current && !selectRef.current?.contains(event.target)) {
        setShowOptions(false)
      }
    })
  }, [])

  const handleSelectedOption = (option) => {
    if (option.disabled) {
      return
    }

    setSelectedOption(option)
    typeof onChange !== 'undefined' && onChange(option.value)
  }

  return (
    <Input label={label} error={error} help={help} className={className}>
      <div ref={selectRef} className={`input ${styles.select} ${showOptions ? styles.opened : ''}`} onClick={() => setShowOptions(!showOptions)}>
        <div className={styles.selected}>
          <SelectOption option={selectedOption ?? defaultOption} className={optionClassName}>
            {children}
          </SelectOption>
        </div>
        <span className={styles.arrow}>
          <FontAwesomeIcon className={styles.arrowIcon} icon={faChevronUp} />
          <FontAwesomeIcon className={styles.arrowIcon} icon={faChevronDown} />
        </span>
        <div className={`${styles.options} ${showOptions ? styles.opened : ''}`}>
          {options.map((option, index) => (
            <SelectOption
              key={index}
              option={option}
              className={optionClassName}
              onClick={option => handleSelectedOption(option)}
            >
              {children}
            </SelectOption>
          ))}
        </div>
      </div>
    </Input>
  )
}

