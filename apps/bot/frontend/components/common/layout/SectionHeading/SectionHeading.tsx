import styles from './SectionHeading.module.css'

interface SectionHeadingProps {
  children: JSX.Element
}

export default function SectionHeading({ children }: SectionHeadingProps) {
  return (
    <div className={styles.sectionHeading}>
      {children}
    </div>
  )
}
