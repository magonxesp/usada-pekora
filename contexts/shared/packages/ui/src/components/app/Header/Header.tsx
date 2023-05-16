import styles from './Header.module.css'
import logo from './logo.png'
import { Picture } from '../../common/image/Picture/Picture'
import Link from 'next/link'

export function Header() {
  return (
    <header className={styles.header}>
      <div className={styles.innerContainer}>
        <Link href="/" className={styles.logo}>
          <img
            src={logo}
            alt="The header logo"
          />
        </Link>
        {/*{(session) ? (
          <div className={styles.currentUser}>
            <Picture
              src={session?.user?.image ?? ''}
              alt="User avatar"
            />
            <p>{session?.user?.name}</p>
          </div>
        ) : ''}*/}
      </div>
    </header>
  )
}
