import styles from './SidebarTitle.module.css'

interface SidebarTitleProps {
  children: string
}

export default function SidebarTitle({ children }: SidebarTitleProps) {
  return <span className={styles.sidebarTitle}>{children}</span>
}
