import styles from './SectionHeading.module.css'

export function SectionHeading({ children }) {
  return (
    <div className={styles.sectionHeading}>
      {children}
    </div>
  )
}
