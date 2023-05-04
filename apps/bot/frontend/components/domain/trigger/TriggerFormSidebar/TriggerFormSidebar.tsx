import { useEffect, useState } from 'react'
import { TriggerFormData } from '../../../../shared/helpers/form/trigger/form-data'
import Sidebar from '../../../common/layout/Sidebar/Sidebar'
import TriggerForm from '../TriggerForm/TriggerForm'
import SidebarHeading from '../../../common/layout/SidebarHeading/SidebarHeading'
import SidebarBody from '../../../common/layout/SidebarBody/SidebarBody'
import SidebarTitle from '../../../common/layout/SidebarTitle/SidebarTitle'

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
        .catch((error) => console.error(error))
        .finally(() => setDisableSubmit(false))
    }
  }

  return (
    <Sidebar show={isOpened}>
      <>
        <SidebarHeading onClose={closeSidebar}>
          <SidebarTitle>{title}</SidebarTitle>
        </SidebarHeading>
        <SidebarBody>
          <TriggerForm
            triggerFormData={initialFormData}
            onSubmit={handleSubmit}
            disableSubmit={disableSubmit}
          />
        </SidebarBody>
      </>
    </Sidebar>
  )
}
