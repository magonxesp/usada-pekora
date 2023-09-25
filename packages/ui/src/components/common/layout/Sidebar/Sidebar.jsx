import styles from './Sidebar.module.css'

export function Sidebar({ children, show }) {
  return (
    <div className={`${styles.sidebar} ${(show) ? styles.show : ""}`}>
      {children}
    </div>
  )
}
