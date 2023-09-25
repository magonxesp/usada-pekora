import styles from './CardList.module.css'

export function CardList({ children, className }) {
  return (
    <div className={`${styles.cardList} ${className ?? ''}`}>
      {children}
    </div>
  )
}
