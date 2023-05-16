import { useEffect, useRef, useState } from 'react'
import styles from './Tooltip.module.css'
import { faCircleInfo } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { v4 as uuidv4 } from 'uuid'

interface TooltipProps {
  content: string
  className?: string
}

export function Tooltip({ content, className }: TooltipProps) {
  const [show, setShow] = useState(false)
  const [id, setId] = useState("")
  const ref = useRef<HTMLSpanElement>(null)

  useEffect(() => {
    setId(uuidv4())

    document.addEventListener('mousedown', () => {
      if (ref.current && ref.current.id !== id) {
        setShow(false)
      }
    })
  }, [])

  return (
    <span
      id={id}
      className={`${styles.tooltip} ${className ?? ''}`}
      ref={ref}
      onClick={(event) => {
        event.stopPropagation()
        setShow(!show)
      }}
    >
      <FontAwesomeIcon icon={faCircleInfo} />
      {(show) ? (
        <span className={styles.content} dangerouslySetInnerHTML={{ __html: content }}></span>
      ) : ''}
    </span>
  )
}

