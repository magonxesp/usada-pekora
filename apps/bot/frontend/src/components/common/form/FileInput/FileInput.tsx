import Input, { InputProps } from '../Input/Input'
import styles from './FileInput.module.css'
import React, { useRef, useState } from 'react'
import { faArrowUpFromBracket } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useIntl } from 'react-intl'
import { faFile } from '@fortawesome/free-regular-svg-icons'
import * as mimes from './FileInputMimeTypes.json'
import Button from '../Button/Button'

type MimeType = keyof typeof mimes

interface TextInputProps extends InputProps<File|null> {
  allowedMimeTypes?: MimeType[]
}

export default function FileInput({ label, help, error, defaultValue, onChange, allowedMimeTypes }: TextInputProps) {
  const inputRef = useRef<HTMLInputElement>(null)
  const [dropping, setDropping] = useState(false)
  const [file, setFile] = useState(defaultValue ?? null)
  const intl = useIntl()

  const extensions = (allowedMimeTypes ?? [])
    .map(mimeType => `.${mimes[mimeType][0]}`)
    .join(', ')

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
              <span>
                {intl.$t({ id: 'input.file.help.intro' })}
                {(extensions != '') ? ` ${intl.$t({ id: 'input.file.help.allowed_types' })} ${extensions}` : ''}
              </span>
            </>
          )}
        </div>
        {(file) ? (
          <div className={styles.actions}>
            <Button onClick={() => inputRef.current?.click()}>{intl.$t({ id: 'input.file.change' })}</Button>
            <Button style="danger" onClick={() => handleFile(null)}>{intl.$t({ id: 'input.file.delete' })}</Button>
          </div>
        ) : ''}
        <input
          type="file"
          multiple={false}
          accept={allowedMimeTypes?.join('')}
          className={styles.input}
          ref={inputRef}
          onChange={event => handleFile(event.target.files?.item(0) ?? null)}
        />
      </>
    </Input>
  )
}
