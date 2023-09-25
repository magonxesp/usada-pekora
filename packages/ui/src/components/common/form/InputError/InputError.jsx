import styles from './InputError.module.css'


export function InputError({ children }) {
  return (
    <>
      {[...children].map((error, index) => (
        <span key={index} className={styles.error}>{error}</span>
      ))}
    </>
  )
}
