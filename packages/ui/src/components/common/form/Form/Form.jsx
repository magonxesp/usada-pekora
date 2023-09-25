import styles from './Form.module.css'


export function Form({ children, onSubmit, className }) {
  const handleFormSubmit = (event) => {
    event.preventDefault()
    onSubmit()
  }

  return (
    <form onSubmit={handleFormSubmit} className={`${styles.form} ${className ?? ''}`}>
      {children}
    </form>
  )
}
