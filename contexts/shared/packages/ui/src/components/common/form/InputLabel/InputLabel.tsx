import styles from './InputLabel.module.css'
import Tooltip from '../../misc/Tooltip/Tooltip'

interface InputLabelProps {
  children: string
  help?: string
  forInput?: string
}

export default function InputLabel({ forInput, help, children }: InputLabelProps) {
  return (
    <div className={styles.label}>
      <label htmlFor={forInput}>{children}</label>
      {(help) ? (
        <Tooltip className={styles.tooltip} content={help}></Tooltip>
      ) : ''}
    </div>
  )
}
