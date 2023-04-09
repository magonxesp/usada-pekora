import { useEffect, useState } from 'react'
import { emptyTriggerFormData, TriggerFormData } from '../../../../shared/helpers/form/trigger'
import Sidebar from '../../../shared/sidebar/Sidebar'
import TriggerForm from './TriggerForm'

interface TriggerFormSidebarProps {
  onSidebarClose?: () => void
  onSubmit?: (data: TriggerFormData) => Promise<void>
  title: string
  initialFormData?: TriggerFormData
}

export default function TriggerFormSidebar({ onSidebarClose, onSubmit, title, initialFormData }: TriggerFormSidebarProps) {
  const [isOpened, setIsOpened] = useState(false)
  const [disableSubmit, setDisableSubmit] = useState(false)

  useEffect(() => {
    setTimeout(() => setIsOpened(true), 100)
  }, [])

  const closeSidebar = () => {
    setIsOpened(false)

    if (typeof onSidebarClose !== 'undefined') {
      onSidebarClose()
    }
  }

  const handleSubmit = (data: TriggerFormData) => {
    if (typeof onSubmit !== 'undefined') {
      setDisableSubmit(true)

      onSubmit(data)
        .then(() => closeSidebar())
        .finally(() => setDisableSubmit(false))
    }
  }

  return (
    <Sidebar show={isOpened}>
      <Sidebar.Header onClose={closeSidebar}>
        <h2 className="heading-2 py-0">
          {title}
        </h2>
      </Sidebar.Header>
      <Sidebar.Body>
        <TriggerForm
          triggerFormData={initialFormData ?? emptyTriggerFormData()}
          onSubmit={handleSubmit}
          disableSubmit={disableSubmit}
        />
      </Sidebar.Body>
    </Sidebar>
  )
}