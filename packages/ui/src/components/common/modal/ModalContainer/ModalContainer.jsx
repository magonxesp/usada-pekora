import styles from './ModalContainer.module.css'
import { useModalStore } from '../../../../store/modal'

export function ModalContainer() {
  const show = useModalStore(state => state.show)
  const component = useModalStore(state => state.component)

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
