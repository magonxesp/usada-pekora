import styles from '../Modal/Modal.module.css'
import { useAppSelector } from '../../../../modules/shared/hooks'

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
