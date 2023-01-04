import { ReactElement, Component } from 'react'

export function isComponent(component: ReactElement, name: string): boolean {
  if (typeof component.type == 'function') {
    return component.type.name == name
  }

  return false
}
