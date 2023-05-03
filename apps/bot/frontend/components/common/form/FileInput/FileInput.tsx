import Input, { InputProps } from '../Input/Input'
import styles from './FileInput.module.css'
import React, { useRef, useState } from 'react'
import { faArrowUpFromBracket } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useIntl } from 'react-intl'
import { faFile } from '@fortawesome/free-regular-svg-icons'

interface TextInputProps extends InputProps<File|null> {}

export default function FileInput({ label, help, error, defaultValue, onChange }: TextInputProps) {
  const inputRef = useRef<HTMLInputElement>(null)
  const [dropping, setDropping] = useState(false)
  const [file, setFile] = useState(defaultValue ?? null)
  const intl = useIntl()

  const handleFile = (file: File|null) => {
    typeof onChange !== 'undefined' && onChange(file)
    setFile(file)
    setDropping(false)
  }

  return (
    <Input
      label={label}
      help={help}
      error={error}
      className={styles.fileInput}
    >
      <>
        <div
          className={`input ${styles.inputContainer} ${dropping ? styles.active : ''}`}
          onClick={() => inputRef.current?.click()}
          onDragOver={(event) => {
            event.preventDefault()
            event.stopPropagation()
            setDropping(true)
          }}
          onDragLeave={() => setDropping(false)}
          onDrop={(event) => {
            event.preventDefault()
            event.stopPropagation()
            handleFile(event.dataTransfer.files?.item(0) ?? null)
          }}
        >
          {(file) ? (
            <>
              <FontAwesomeIcon icon={faFile} />
              <span>{file.name}</span>
            </>
          ) : (
            <>
              <FontAwesomeIcon icon={faArrowUpFromBracket} />
              <span>{intl.$t({ id: 'input.file.help' })}</span>
            </>
          )}
        </div>
        <input
          type="file"
          multiple={false}
          className={styles.input}
          ref={inputRef}
          onChange={event => handleFile(event.target.files?.item(0) ?? null)}
        />
      </>
    </Input>
  )
}
