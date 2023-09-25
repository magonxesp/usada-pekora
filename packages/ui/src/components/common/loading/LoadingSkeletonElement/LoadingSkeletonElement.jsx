import styles from './LoadingSkeletonElement.module.css'

export function LoadingSkeletonElement({ children, loaded }) {
  return (
    <>
      {(loaded) ? children : (
        <div className={styles.skeleton}>
          {children}
        </div>
      )}
    </>
  )
}
