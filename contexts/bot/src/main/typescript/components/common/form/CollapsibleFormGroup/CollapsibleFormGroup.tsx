import { MinusCircleIcon, PlusCircleIcon } from '@heroicons/react/24/outline'
import { MinusCircleIcon as SolidMinusCircleIcon, PlusCircleIcon as SolidPlusCircleIcon } from '@heroicons/react/24/solid'
import { useState } from 'react'
import styles from './CollapsibleFormGroup.module.css'
import Button from '../Button/Button'

interface CollapsibleFormGroupProps {
  children?: JSX.Element
  onRemove?: () => void
  addTitle?: string
  removeTitle?: string
  open?: boolean
}

export default function CollapsibleFormGroup({ children, onRemove, addTitle, removeTitle, open }: CollapsibleFormGroupProps) {
  const [add, setAdd] = useState(open ?? false)

  const handleRemove = () => {
    setAdd(false)

    if (typeof onRemove !== 'undefined') {
      onRemove()
    }
  }

  return (
    <div>
      {(!add) ? (
        <Button style="transparent" className={styles.button} onClick={() => setAdd(true)}>
          <>
            <PlusCircleIcon className={styles.buttonIcon}></PlusCircleIcon>
            <SolidPlusCircleIcon className={`${styles.buttonIcon} ${styles.active}`}></SolidPlusCircleIcon>
            {addTitle ?? "Add"}
          </>
        </Button>
      ) : (
        <>
          <Button style="transparent" className={styles.button} onClick={handleRemove}>
            <>
              <MinusCircleIcon className={styles.buttonIcon}></MinusCircleIcon>
              <SolidMinusCircleIcon className={`${styles.buttonIcon} ${styles.active}`}></SolidMinusCircleIcon>
              {removeTitle ?? "Remove"}
            </>
          </Button>
          {children}
        </>
      )}
    </div>
  )
}
