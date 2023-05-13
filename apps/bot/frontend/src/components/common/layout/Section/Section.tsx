import styles from './Section.module.css'

interface SectionProps {
  children: JSX.Element
}

export default function Section({ children }: SectionProps) {
  return (
    <section className={styles.section}>
      {children}
    </section>
  )
}
