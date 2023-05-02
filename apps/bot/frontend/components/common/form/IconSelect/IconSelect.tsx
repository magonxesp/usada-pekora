import { useEffect, useRef, useState } from 'react'
import styles from './IconSelect.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronDown, faChevronUp, faQuestion } from '@fortawesome/free-solid-svg-icons'
import Picture from '../../image/Picture/Picture'
import Select, { SelectProps, Option as SelectOption } from '../Select/Select'

export interface Option extends SelectOption {
  icon: string
}

interface IconSelectProps extends SelectProps<Option> {
  selected?: string|number
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

export default function IconSelect({ options, onChange, className, label, help, error, defaultValue }: IconSelectProps) {
  return (
    <Select
      options={options}
      label={label}
      help={help}
      error={error}
      className={`${styles.iconSelect} ${className ?? ''}`}
      optionClassName={styles.option}
      onChange={onChange}
      defaultValue={defaultValue}
    >
      {(option: Option) => (
        <>
          {(option.icon == "") ? (
            <div className={styles.emptyImage}><FontAwesomeIcon icon={faQuestion} /></div>
          ) : (
            <Picture src={option.icon} alt={option.label} />
          )}
          {option.label}
        </>
      )}
    </Select>
  )
}

