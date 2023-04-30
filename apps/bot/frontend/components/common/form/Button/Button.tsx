import { MouseEvent } from 'react'
import styles from './Button.module.css'

type ButtonStyle = 'primary'
  | 'secondary'
  | 'danger'
  | 'transparent'

interface ButtonProps {
  style?: ButtonStyle
  children: JSX.Element|string
  type?: 'button' | 'submit' | 'reset'
  onClick?: (event: MouseEvent) => void
  disabled?: boolean
}

export default function Button({ style, type, onClick, disabled, children }: ButtonProps) {
  const styleClass = {
    'transparent': styles.transparent,
    'secondary': styles.secondary,
    'danger': styles.danger,
    'primary': ''
  }

  return (
    <button
      className={`${styles.button} ${styleClass[style ?? 'primary']}`}
      onClick={(event) => onClick && onClick(event)}
      type={type ?? 'button'}
      disabled={disabled ?? false}
    >{children}</button>
  )
}
