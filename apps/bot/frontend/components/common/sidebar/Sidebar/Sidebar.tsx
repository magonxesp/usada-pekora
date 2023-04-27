import styles from "./Sidebar.module.css"
import { ReactElement } from 'react'
import { JSX } from 'preact'
import { isComponent } from '../../../../shared/helpers/runtime'
import Button from '../../form/Button/Button'
import { XMarkIcon } from '@heroicons/react/24/outline'

interface SidebarProps {
  children: ReactElement|ReactElement[]
  show?: boolean
}

interface HeaderProps {
  children?: ReactElement
  onClose?: () => void
}

function Header({ children, onClose }: HeaderProps) {
  return (
    <>
      <div className="flex items-center justify-between">
        {children ?? (<div></div>)}
        <Button className="relative" color="transparent" onClick={onClose ?? (() => {})}>
          <XMarkIcon className="w-5" />
        </Button>
      </div>
      <hr className="my-5" />
    </>
  )
}

function Body({ children }: { children: JSX.Element }) {
  return <>{children}</>
}

function Sidebar({ children, show }: SidebarProps) {
  return (
    <div className={`absolute top-0 bg-white h-full px-5 py-7 shadow-xl ${styles.sidebar} ${(show) ? styles.show : ""}`}>
      {children}
    </div>
  )
}

Sidebar.Header = Header
Sidebar.Body = Body

export default Sidebar
