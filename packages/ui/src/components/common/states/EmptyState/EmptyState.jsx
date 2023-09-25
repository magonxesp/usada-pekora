import image from './empty-image.jpg'
import styles from './EmptyState.module.css'

export function EmptyState(props) {
  return (
    <div className={styles.emptyState}>
      <img src={image} alt="Empty state image" />
      <p>{props.message ?? 'Esto esta vacio y lo sabes'}</p>
    </div>
  )
}
