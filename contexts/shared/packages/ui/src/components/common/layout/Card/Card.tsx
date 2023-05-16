import styles from './Card.module.css'

interface CardProps {
  children: JSX.Element
  className?: string
}

export function Card({ children, className }: CardProps) {
  return (
    <div className={`${styles.card} ${className ?? ''}`}>
      {children}
    </div>
  )
}
