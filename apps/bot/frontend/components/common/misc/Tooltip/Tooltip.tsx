import { createRef, useEffect, useState } from 'react'
import styles from './Tooltip.module.css'
import { faCircleInfo } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

interface TooltipProps {
  content: string
  className?: string
}

export default function Tooltip({ content, className }: TooltipProps) {
  const [show, setShow] = useState(false)
  const ref = createRef<HTMLSpanElement>()

  useEffect(() => {
    document.querySelector('body')?.addEventListener('click', (event: MouseEvent) => {
      const target = event.target as HTMLElement

      if (!target.closest('.tooltip')) {
        setShow(false)
      }
    })
  }, [])

  return (
    <span className={`${styles.tooltip} ${className ?? ''}`} ref={ref}>
      <FontAwesomeIcon icon={faCircleInfo} onClick={() => setShow(!show)} />
      {(show) ? (
        <span className={styles.content} dangerouslySetInnerHTML={{ __html: content }}></span>
      ) : ''}
    </span>
  )
}

