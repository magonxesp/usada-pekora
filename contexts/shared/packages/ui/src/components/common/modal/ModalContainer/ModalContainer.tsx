import styles from './ModalContainer.module.css'
import { createContext, useContext } from 'react'

export const ModalShowContext = createContext(false)
export const ModalComponentContext = createContext(null)

export default function ModalContainer() {
  const show = useContext(ModalShowContext)
  const component = useContext(ModalComponentContext)

  return (
    <>
      {show ? (
        <div className={styles.backdrop}>
          {component ?? ""}
        </div>
      ) : ""}
    </>
  )
}
