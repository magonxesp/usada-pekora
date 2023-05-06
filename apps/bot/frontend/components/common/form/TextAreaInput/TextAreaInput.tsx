import Input, { InputProps } from '../Input/Input'
import styles from './TextAreaInput.module.css'
import { useState } from 'react'

interface TextAreaInputProps extends InputProps<string> {
  limit?: number
}

export default function TextAreaInput({ label, help, error, defaultValue, onChange, limit }: TextAreaInputProps) {
  const [characterCount, setCharacterCount] = useState(0)

  return (
    <Input
      label={label}
      help={help}
      error={error}
      className={styles.textAreaInput}
    >
      <>
        {(limit) ? (
          <div className={styles.counter}>
            {characterCount}/{limit}
          </div>
        ) : ''}
        <textarea
          defaultValue={defaultValue}
          onChange={event => {
            let value = event.target.value
            setCharacterCount(value.length)

            if (limit) {
              value = value.substring(0, limit)
            }

            typeof onChange !== 'undefined' && onChange(value)
          }}
          maxLength={limit}
        />
      </>
    </Input>
  )
}
