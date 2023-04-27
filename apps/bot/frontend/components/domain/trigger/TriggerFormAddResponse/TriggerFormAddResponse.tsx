import { MinusCircleIcon, PlusCircleIcon } from '@heroicons/react/24/outline'
import { useState } from 'react'

interface TriggerFormAddResponseProps {
  children?: JSX.Element
  onRemove?: () => void
  addTitle?: string
  removeTitle?: string
}

export default function TriggerFormAddResponse({ children, onRemove, addTitle, removeTitle }: TriggerFormAddResponseProps) {
  const [add, setAdd] = useState(false)
  const buttonClasses = [
    'flex',
    'items-center',
    'text-lg',
    'text-primary',
    'hover:underline',
    'cursor-pointer'
  ].join(' ')

  const handleRemove = () => {
    setAdd(false)

    if (typeof onRemove !== 'undefined') {
      onRemove()
    }
  }

  return (
    <>
      {(!add) ? (
        <p className={buttonClasses} onClick={() => setAdd(true)}>
          <PlusCircleIcon className="w-6 mr-1.5"></PlusCircleIcon> {addTitle ?? "Add"}
        </p>
      ) : (
        <>
          <p className={buttonClasses} onClick={handleRemove}>
            <MinusCircleIcon className="w-6 mr-1.5"></MinusCircleIcon> {removeTitle ?? "Remove"}
          </p>
          {children}
        </>
      )}
    </>
  )
}
