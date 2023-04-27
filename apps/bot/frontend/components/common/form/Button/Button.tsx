import { ColorString } from '../../../../shared/helpers/color'
import { MouseEvent } from 'react'

interface ButtonProps<T> {
  color?: ColorString
  children: any
  value?: T
  type?: 'button' | 'submit' | 'reset'
  onClick?: (event: MouseEvent, value: T|undefined) => void
  disabled?: boolean
  className?: string
}

export default function Button<T>(props: ButtonProps<T>) {
  const color = (props.color) ? `bg-${props.color}` : 'bg-primary'

  return (
    <button
      className={`${(color != 'transparent') ? color : ''} ${props.className ?? ''}`}
      onClick={(event) => props.onClick && props.onClick(event, props.value)}
      type={props.type ?? 'button'}
      disabled={props.disabled ?? false}
    >{props.children}</button>
  )
}
