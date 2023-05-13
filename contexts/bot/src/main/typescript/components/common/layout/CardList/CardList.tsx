import styles from './CardList.module.css'

interface CardListProps {
  children: JSX.Element
  className?: string
}

export default function CardList({ children, className }: CardListProps) {
  return (
    <div className={`${styles.cardList} ${className ?? ''}`}>
      {children}
    </div>
  )
}
