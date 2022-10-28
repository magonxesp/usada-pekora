import { ColorString } from '../../../modules/shared/domain/color/color'
import { MouseEvent } from 'react'

interface ButtonProps<T> {
  color?: ColorString
  children: any
  value?: T,
  onClick?: (event: MouseEvent, value: T|undefined) => void
}

export default function Button<T>(props: ButtonProps<T>) {
  const color = (props.color) ? `bg-${props.color}` : ''

  return (
    <button
      className={color}
      onClick={(event) => props.onClick && props.onClick(event, props.value)}
    >{props.children}</button>
  )
}
