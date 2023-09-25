import styles from './SidebarTitle.module.css'

export function SidebarTitle({ children }) {
  return <span className={styles.sidebarTitle}>{children}</span>
}
