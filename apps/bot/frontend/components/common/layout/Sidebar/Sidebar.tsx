import styles from "./Sidebar.module.css"

interface SidebarProps {
  children: JSX.Element
  show?: boolean
}

export default function Sidebar({ children, show }: SidebarProps) {
  return (
    <div className={`${styles.sidebar} ${(show) ? styles.show : ""}`}>
      {children}
    </div>
  )
}
