import styles from './IconSelect.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faQuestion } from '@fortawesome/free-solid-svg-icons'
import { Picture } from '../../image/Picture/Picture'
import { Select } from '../Select/Select'

function IconSelectOption({ option, onClick }) {
  return (
    <div className={`option ${styles.option}`} onClick={() => typeof onClick !== 'undefined' && onClick(option)}>
      {(option.icon == "") ? (
        <div className={styles.emptyImage}><FontAwesomeIcon icon={faQuestion} /></div>
      ) : (
        <Picture src={option.icon} alt={option.label} />
      )}
      {option.label}
    </div>
  )
}

export function IconSelect({ options, onChange, className, label, help, error, defaultValue }) {
  return (
    <Select
      options={options}
      label={label}
      help={help}
      error={error}
      className={`${styles.iconSelect} ${className ?? ''}`}
      optionClassName={styles.option}
      onChange={onChange}
      defaultValue={defaultValue}
    >
      {(option) => (
        <>
          {(option.icon == "") ? (
            <div className={styles.emptyImage}><FontAwesomeIcon icon={faQuestion} /></div>
          ) : (
            <Picture src={option.icon} alt={option.label} />
          )}
          {option.label}
        </>
      )}
    </Select>
  )
}

