import { ColorString } from '../../../modules/shared/domain/color/color'

interface ButtonProps {
  color?: ColorString
  children: any
  value?: any,
  onClick?: (value: any) => void
}

export default function Button(props: ButtonProps) {
  const color = (props.color) ? `bg-${props.color}` : ''

  return (
    <button className={color} onClick={() => props.onClick && props.onClick(props.value)}>{props.children}</button>
  )
}
