import styles from './Modal.module.css'
import { useAppSelector } from '../../../shared/hooks/store'

export default function ModalContainer() {
  const show = useAppSelector((state) => state.app.modal.show)
  const component = useAppSelector((state) => state.app.modal.component)

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
