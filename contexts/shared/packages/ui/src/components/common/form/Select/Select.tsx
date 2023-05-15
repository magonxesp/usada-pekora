import { useEffect, useRef, useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronDown, faChevronUp } from '@fortawesome/free-solid-svg-icons'
import Input, { InputProps } from '../Input/Input'
import styles from './Select.module.css'

export interface SelectOption {
  label: string
  value: string|number
  disabled?: boolean
}

export interface SelectProps<T extends SelectOption> extends InputProps<string|number> {
  options: T[]
  optionClassName?: string
  children?: (option: T) => JSX.Element
}

interface SelectOptionProps<T extends SelectOption> {
  option: T
  onClick?: (option: T) => void
  children?: (option: T) => JSX.Element
  className?: string
}

function SelectOption<T extends SelectOption>({ option, onClick, children, className }: SelectOptionProps<T>) {
  return (
    <div className={`${styles.option} ${className ?? ''}`} onClick={() => typeof onClick !== 'undefined' && onClick(option)}>
      {(typeof children !== 'undefined') ? children(option) : ''}
    </div>
  )
}

export default function Select<T extends SelectOption>({
  options,
  onChange,
  defaultValue,
  label,
  error,
  help,
  className,
  optionClassName,
  children
}: SelectProps<T>) {
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

  const handleSelectedOption = (option: T) => {
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
          <SelectOption<T> option={selectedOption ?? defaultOption as T} className={optionClassName}>
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

