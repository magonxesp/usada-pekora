import React, { useState } from 'react'
import styles from './InputWrapper.module.css'
import {
  InputChangeEventHandler,
  InputWrapperOnChangeEventHandler,
} from '../../../../modules/shared/infraestructure/react/form/input'


interface InputWrapperProps {
  label: string
  children: (eventHandler: InputChangeEventHandler) => React.ReactElement
  className?: string

  /**
   * onChange event
   *
   * @param {string} name
   * @param {any} value
   */
  onChange?: InputWrapperOnChangeEventHandler

  /**
   * Validate the input value when onChange event is fired
   *
   * @param {string} value
   *
   * @throws {string}
   *    Throws string with the error message in case the value is not valid
   */
  validate?: (value: any) => void
}

export default function InputWrapper({ label, children, onChange, validate, className }: InputWrapperProps) {
  const [error, setError] = useState('')

  const handleOnChange: InputChangeEventHandler = (event) => {
    setError('')

    if (typeof onChange !== 'undefined') {
      onChange(event.target.name, event.target.value)
    }

    if (typeof validate !== 'undefined') {
      try {
        validate(event.target.value)
      } catch (error) {
        if (typeof error === 'string') {
          setError(error)
        }
      }
    }
  }

  return (
    <div className={`${styles.wrapper} space-y-2 ${className ?? ''}`}>
      <label>{label}</label>
      <>
        {children(handleOnChange)}
      </>
      <span className="text-red-700">{error}</span>
    </div>
  )
}
