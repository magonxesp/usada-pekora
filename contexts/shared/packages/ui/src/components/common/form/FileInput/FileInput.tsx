import { Input, InputProps } from '../Input/Input'
import styles from './FileInput.module.css'
import { useRef, useState } from 'react'
import { faArrowUpFromBracket } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faFile } from '@fortawesome/free-regular-svg-icons'
import * as mimes from './FileInputMimeTypes.json'
import { Button } from '../Button/Button'

export type MimeType = "text/html"
  | "text/css"
  | "text/xml"
  | "image/gif"
  | "image/jpeg"
  | "application/x-javascript"
  | "application/atom+xml"
  | "application/rss+xml"
  | "text/mathml"
  | "text/plain"
  | "text/vnd.sun.j2me.app-descriptor"
  | "text/vnd.wap.wml"
  | "text/x-component"
  | "image/png"
  | "image/tiff"
  | "image/vnd.wap.wbmp"
  | "image/x-icon"
  | "image/x-jng"
  | "image/x-ms-bmp"
  | "image/svg+xml"
  | "image/webp"
  | "application/java-archive"
  | "application/mac-binhex40"
  | "application/msword"
  | "application/pdf"
  | "application/postscript"
  | "application/rtf"
  | "application/vnd.ms-excel"
  | "application/vnd.ms-powerpoint"
  | "application/vnd.wap.wmlc"
  | "application/vnd.google-earth.kml+xml"
  | "application/vnd.google-earth.kmz"
  | "application/x-7z-compressed"
  | "application/x-cocoa"
  | "application/x-java-archive-diff"
  | "application/x-java-jnlp-file"
  | "application/x-makeself"
  | "application/x-perl"
  | "application/x-pilot"
  | "application/x-rar-compressed"
  | "application/x-redhat-package-manager"
  | "application/x-sea"
  | "application/x-shockwave-flash"
  | "application/x-stuffit"
  | "application/x-tcl"
  | "application/x-x509-ca-cert"
  | "application/x-xpinstall"
  | "application/xhtml+xml"
  | "application/zip"
  | "application/octet-stream"
  | "audio/midi"
  | "audio/mpeg"
  | "audio/ogg"
  | "audio/x-realaudio"
  | "video/3gpp"
  | "video/mpeg"
  | "video/quicktime"
  | "video/x-flv"
  | "video/x-mng"
  | "video/x-ms-asf"
  | "video/x-ms-wmv"
  | "video/x-msvideo"
  | "video/mp4"

interface FileInputProps extends InputProps<File|string|null> {
  allowedMimeTypes?: MimeType[]
  helpText?: string
  allowedTypesText?: string
  changeText?: string
  deleteText?: string
}

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
}: FileInputProps) {
  const inputRef = useRef<HTMLInputElement>(null)
  const [dropping, setDropping] = useState(false)
  const [file, setFile] = useState(defaultValue ?? null)

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
              <span>{(typeof File !== 'undefined' && file instanceof File) ? file.name : file as string}</span>
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
