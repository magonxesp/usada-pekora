import error from './error.png'
import styles from './ErrorView.module.css'
import { Button } from '../../common/form/Button/Button'

export function ErrorView({ statusCode, message, actionMessage, onAction }) {
  return (
    <div className={styles.error}>
      <img src={error} alt="Error image" />
      <span>{statusCode}</span>
      <span>{message}</span>
      {(actionMessage) ? (
        <Button className={styles.action} onClick={onAction}>{actionMessage}</Button>
      ) : ''}
    </div>
  )
}
