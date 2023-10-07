import styles from './CurrentUser.module.css'
import { Picture } from '../../common/image/Picture/Picture'
import { useIntl } from 'react-intl'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faDoorClosed } from '@fortawesome/free-solid-svg-icons'
import { useRef, useState, useEffect } from 'react'

export function CurrentUser({ user }) {
  const { $t } = useIntl()
  const [open, setOpen] = useState(false)
  const menuRef = useRef(null)
  const avatarRef = useRef(null)

  useEffect(() => {
    document.addEventListener('mousedown', (event) => {
      if (event.target instanceof HTMLElement 
        && menuRef.current 
        && !menuRef.current?.contains(event.target)
        && !avatarRef.current?.contains(event.target)) {
        setOpen(false)
      }
    })
  }, [])

  return (
    <div>
      <div ref={avatarRef} className={styles.currentUser} onClick={() => setOpen(!open)}>
        <Picture src={user.avatar ?? ""} alt="User avatar" />
        <p>{user.name}</p>
      </div>
      {(open) ? (
        <ul className={styles.menu} ref={menuRef}>
          <li>
            <a href={`${process.env.NEXT_PUBLIC_AUTH_FRONTEND_BASE_URL}/api/logout`}>
              <FontAwesomeIcon icon={faDoorClosed} className={styles.menuItemIcon} />
              {$t({ id: 'current_user.logout' })}
            </a>
          </li>
        </ul>
      ) : ''}
    </div>
  );
}
