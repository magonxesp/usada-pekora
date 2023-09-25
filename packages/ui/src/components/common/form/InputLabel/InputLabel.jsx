import styles from './InputLabel.module.css'
import { Tooltip } from '../../misc/Tooltip/Tooltip'


export function InputLabel({ forInput, help, children }) {
  return (
    <div className={styles.label}>
      <label htmlFor={forInput}>{children}</label>
      {(help) ? (
        <Tooltip className={styles.tooltip} content={help}></Tooltip>
      ) : ''}
    </div>
  )
}
