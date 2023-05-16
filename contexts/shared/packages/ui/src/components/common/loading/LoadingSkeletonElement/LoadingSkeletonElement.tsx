import styles from './LoadingSkeletonElement.module.css'

interface SkeletonElementProps {
  loaded: boolean
  children?: JSX.Element
}

export function LoadingSkeletonElement({ children, loaded }: SkeletonElementProps) {
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
