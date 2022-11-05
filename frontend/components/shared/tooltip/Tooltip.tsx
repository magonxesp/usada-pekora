import { InformationCircleIcon } from '@heroicons/react/24/outline'
import { createRef, useEffect, useState } from 'react'

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

  const handleClick = () => {
    setShow(!show)
  }

  return (
    <span className={`tooltip inline-block relative ${className ?? ''}`} ref={ref}>
      <InformationCircleIcon className="hover:cursor-pointer hover:text-primary" onClick={handleClick} />
      {(show) ? (
        <span className="absolute w-80 bg-white p-3 text-sm shadow-xl rounded border mt-2" dangerouslySetInnerHTML={{ __html: content }}></span>
      ) : ''}
    </span>
  )
}

