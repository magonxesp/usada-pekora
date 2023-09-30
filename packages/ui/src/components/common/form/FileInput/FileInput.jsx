import { Input } from '../Input/Input'
import styles from './FileInput.module.css'
import { useRef, useState } from 'react'
import { faArrowUpFromBracket } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faFile } from '@fortawesome/free-regular-svg-icons'
import mimes from './FileInputMimeTypes.json'
import { Button } from '../Button/Button'

export function FileInput({ 
  label, 
  help, 
  error, 
  defaultValue,
  onChange, 
  allowedMimeTypes,
  helpText,
  allowedTypesText,
  changeText,
  deleteText
}) {
  const inputRef = useRef(null)
  const [dropping, setDropping] = useState(false)
  const [file, setFile] = useState(defaultValue ?? null)

  const extensions = (allowedMimeTypes ?? [])
    .map(mimeType => `.${mimes[mimeType][0]}`)
    .join(', ')

  const handleFile = (file) => {
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
              <span>{(typeof File !== 'undefined' && file instanceof File) ? file.name : file}</span>
            </>
          ) : (
            <>
              <FontAwesomeIcon icon={faArrowUpFromBracket} />
              <span>
                {helpText ?? 'Select file or drop here'}
                {(extensions != '') ? ` ${allowedTypesText ?? 'Allowed types:'} ${extensions}` : ''}
              </span>
            </>
          )}
        </div>
        {(file) ? (
          <div className={styles.actions}>
            <Button onClick={() => inputRef.current?.click()}>{changeText ?? 'Change'}</Button>
            <Button style="danger" onClick={() => handleFile(null)}>{deleteText ?? 'Delete'}</Button>
          </div>
        ) : ''}
        <input
          type="file"
          multiple={false}
          accept={allowedMimeTypes?.join('')}
          className={styles.input}
          ref={inputRef}
          onChange={event => handleFile(event.target.files && event.target.files[0])}
        />
      </>
    </Input>
  )
}
