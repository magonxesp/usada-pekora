import Image from 'next/image'
import image from './empty-image.jpg'
import styles from './EmptyState.module.css'

interface EmptyStateProps {
  message?: string
}

export default function EmptyState(props: EmptyStateProps) {
  return (
    <div className={styles.emptyState}>
      <Image src={image} alt="Empty state image" />
      <p>{props.message ?? 'Esto esta vacio y lo sabes'}</p>
    </div>
  )
}
