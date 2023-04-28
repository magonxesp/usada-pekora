import { LegacyRef, useEffect, useRef, useState } from 'react'
import styles from './IconSelect.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronDown, faChevronUp, faQuestion } from '@fortawesome/free-solid-svg-icons'
import Picture from '../../image/Picture/Picture'

export interface Option {
  label: string
  value: string|number
  icon: string
  //disabled?: boolean
}

interface IconSelectProps {
  options: Option[]
  onChange?: (selected: Option) => void
  selected?: string|number
  className?: string
}

interface IconSelectOptionProps {
  option: Option
  onClick?: (option: Option) => void
}

function IconSelectOption({ option, onClick }: IconSelectOptionProps) {
  return (
    <div className={`option ${styles.option}`} onClick={() => typeof onClick !== 'undefined' && onClick(option)}>
      {(option.icon == "") ? (
        <div className={styles.emptyImage}><FontAwesomeIcon icon={faQuestion} /></div>
      ) : (
        <Picture src={option.icon} alt={option.label} />
      )}
      {option.label}
    </div>
  )
}

export default function IconSelect({ options, onChange, selected, className }: IconSelectProps) {
  const defaultOption: Option = {
    icon: "",
    label: "-",
    value: ""
  }

  const initialSelectedState = options.filter(option => option.value === selected).shift()
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

  const handleSelectedOption = (option: Option) => {
    setSelectedOption(option)
    typeof onChange !== 'undefined' && onChange(option)
  }

  return (
    <div ref={selectRef} className={`input select ${styles.select} ${showOptions ? styles.opened : ''} ${className ?? ''}`} onClick={() => setShowOptions(!showOptions)}>
      <div className={styles.selected}>
        <IconSelectOption option={selectedOption ?? defaultOption} />
      </div>
      <div className={`options ${styles.options} ${showOptions ? styles.opened : ''}`}>
        {options.map((option, index) => (
          <IconSelectOption key={index} option={option} onClick={option => handleSelectedOption(option)} />
        ))}
      </div>
      <span className="arrow">
        <FontAwesomeIcon className="arrowIcon" icon={faChevronUp} />
        <FontAwesomeIcon className="arrowIcon" icon={faChevronDown} />
      </span>
    </div>
  )
}

