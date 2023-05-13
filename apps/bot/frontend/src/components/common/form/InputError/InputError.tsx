import styles from './InputError.module.css'

interface InputErrorProps {
  children: string|string[]
}

export default function InputError({ children }: InputErrorProps) {
  return (
    <>
      {[...children].map((error, index) => (
        <span key={index} className={styles.error}>{error}</span>
      ))}
    </>
  )
}
