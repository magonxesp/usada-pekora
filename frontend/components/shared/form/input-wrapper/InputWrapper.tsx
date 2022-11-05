import styles from './InputWrapper.module.css'
import { ExclamationCircleIcon } from '@heroicons/react/24/outline'
import Tooltip from '../../tooltip/Tooltip'

interface InputWrapperProps {
  label: string
  children: JSX.Element
  className?: string
  error?: string,
  hasError?: boolean
  help?: string
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
    <span className="text-sm text-red-700 flex">
      <ExclamationCircleIcon className="w-4 mr-1" />
      {children}
    </span>
  )
}

export default function InputWrapper({ label, children, className, help }: InputWrapperProps) {
  return (
    <div className={`${styles.wrapper} space-y-1 ${className ?? ''}`}>
      <label className="flex items-center">
        {label}
        {(help) ? <Tooltip className="w-4 ml-1" content={help} /> : ''}
      </label>
      {children}
    </div>
  )
}

InputWrapper.Input = Input
InputWrapper.Error = Error
