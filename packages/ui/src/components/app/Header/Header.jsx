import styles from './Header.module.css'
import logo from './logo.png'
import Link from 'next/link'
import { useContext } from 'react'
import { UserContext } from '../../../hooks/contexts'
import { Picture } from '../../common/image/Picture/Picture'

export function Header() {
  const user = useContext(UserContext)

  return (
    <header className={styles.header}>
      <div className={styles.innerContainer}>
        <Link href="/" className={styles.logo}>
          <img
            src={logo}
            alt="The header logo"
          />
        </Link>
        {(user) ? (
          <div className={styles.currentUser}>
            <Picture
              src={user?.avatar ?? ''}
              alt="User avatar"
            />
            <p>{user?.name}</p>
          </div>
        ) : ''}
      </div>
    </header>
  )
}
