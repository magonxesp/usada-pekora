import styles from './Button.module.css'

export function Button({ style, type, onClick, disabled, children, className }) {
  const styleClass = {
    'transparent': styles.transparent,
    'secondary': styles.secondary,
    'danger': styles.danger,
    'primary': ''
  }

  return (
    <button
      className={`${styles.button} ${styleClass[style ?? 'primary']} ${className ?? ''}`}
      onClick={(event) => onClick && onClick(event)}
      type={type ?? 'button'}
      disabled={disabled ?? false}
    >{children}</button>
  )
}
