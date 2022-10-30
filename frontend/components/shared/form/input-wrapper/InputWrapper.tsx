import { ReactElement } from 'react'
import styles from './InputWrapper.module.css'

interface InputWrapperProps {
  label: string
  children: Array<JSX.Element>
  className?: string
  error?: string,
  hasError?: boolean
}

interface InputProps {
  children: JSX.Element
}

interface ErrorProps {
  children: string
}

function Input({ children }: InputProps) {
  return (
    <>{children}</>
  )
}

function Error({ children }: ErrorProps) {
  return (
    <span className="text-red-700">{children}</span>
  )
}

export default function InputWrapper({ label, children, className }: InputWrapperProps) {
  return (
    <div className={`${styles.wrapper} space-y-2 ${className ?? ''}`}>
      <label>{label}</label>
      {children.filter((element) => element.type.name == 'Input')}
      {children.filter((element) => element.type.name == 'Error')}
    </div>
  )
}

InputWrapper.Input = Input
InputWrapper.Error = Error
