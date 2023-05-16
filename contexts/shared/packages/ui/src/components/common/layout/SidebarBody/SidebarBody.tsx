import styles from './SidebarBody.module.css'

interface SidebarProps {
  children: JSX.Element
}


export function SidebarBody({ children }: SidebarProps) {
  return (
    <div className={styles.sidebarBody}>
      {children}
    </div>
  )
}
