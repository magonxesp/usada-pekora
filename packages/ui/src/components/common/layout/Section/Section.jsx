import styles from './Section.module.css'

export function Section({ children }) {
  return (
    <section className={styles.section}>
      {children}
    </section>
  )
}
