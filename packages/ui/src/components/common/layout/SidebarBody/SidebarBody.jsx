import styles from './SidebarBody.module.css'

export function SidebarBody({ children }) {
  return (
    <div className={styles.sidebarBody}>
      {children}
    </div>
  )
}
