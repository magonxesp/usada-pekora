import { Pekora } from '../Pekora/Pekora'
import { LoginProviderButtons } from '../LoginProviderButtons/LoginProviderButtons'
import styles from './LoginForm.module.css'

export function LoginForm() {
  return (
    <div className={styles.loginForm}>
      <Pekora />
      <LoginProviderButtons />
    </div>
  )
}
