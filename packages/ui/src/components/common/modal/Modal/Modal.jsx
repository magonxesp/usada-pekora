import styles from './Modal.module.css'

function ModalBody({ children, className }) {
  return (
    <div className={`w-full ${className ?? ''}`}>
      {children ?? ""}
    </div>
  )
}

function Modal({ children, size }) {
  const sizes = {
    'm': 'w-1/3',
    'l': 'w-2/3',
    'xl': 'w-5/6'
  }

  return (
    <div className={`rounded-md p-5 ${styles.modal} ${sizes[size ?? 'm']}`}>
      {children ?? ""}
    </div>
  )
}

Modal.ModalBody = ModalBody

export { Modal }
