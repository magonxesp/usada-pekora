import styles from './SidebarHeading.module.css'
import { Button } from '../../form/Button/Button'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faXmark } from '@fortawesome/free-solid-svg-icons'

export function SidebarHeading({ children, onClose }) {
  return (
    <>
      <div className={styles.sidebarHeading}>
        {children ?? (<div></div>)}
        <Button style="transparent" onClick={onClose ?? (() => {})}>
          <FontAwesomeIcon icon={faXmark} />
        </Button>
      </div>
      <hr className="my-5" />
    </>
  )
}
