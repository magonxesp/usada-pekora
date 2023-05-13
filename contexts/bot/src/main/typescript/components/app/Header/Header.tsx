import Image from 'next/image'
import styles from './Header.module.css'
import logo from './logo.png'
import { useSession } from 'next-auth/react'
import Picture from '../../common/image/Picture/Picture'
import Link from 'next/link'

export default function Header() {
  const { data: session } = useSession()

  return (
    <header className={styles.header}>
      <div className={styles.innerContainer}>
        <Link href="/" className={styles.logo}>
          <Image
            src={logo}
            alt="The header logo"
            loading="eager"
          />
        </Link>
        {(session) ? (
          <div className={styles.currentUser}>
            <Picture
              src={session?.user?.image ?? ''}
              alt="User avatar"
            />
            <p>{session?.user?.name}</p>
          </div>
        ) : ''}
      </div>
    </header>
  )
}
