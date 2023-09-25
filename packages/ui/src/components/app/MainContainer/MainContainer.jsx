import styles from './MainContainer.module.css'
import React from 'react'

export function MainContainer({ children, as }) {
  const containerProps = {
    className: styles.main
  }

  return React.createElement(as ?? 'main', containerProps, children)
}
