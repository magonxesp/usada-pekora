import styles from './Header.module.css'
import logo from './logo.png'
import Link from 'next/link'
import { useContext } from 'react'
import { UserContext } from '../../../hooks/contexts'
import { CurrentUser } from '../CurrentUser/CurrentUser'

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
          <CurrentUser user={user} />
        ) : ''}
      </div>
    </header>
  )
}
