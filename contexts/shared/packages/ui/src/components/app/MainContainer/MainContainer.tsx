import styles from './MainContainer.module.css'
import React from 'react'

interface MainContainerProps {
  children: JSX.Element
  as?: keyof React.ReactHTML
}

export function MainContainer({ children, as }: MainContainerProps) {
  const containerProps = {
    className: styles.main
  }

  return React.createElement(as ?? 'main', containerProps, children)
}
