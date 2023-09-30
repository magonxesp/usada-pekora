import { useEffect, useState } from 'react'
import { Sidebar, SidebarBody, SidebarHeading, SidebarTitle } from '@usada-pekora/ui/components'
import TriggerForm from '../TriggerForm/TriggerForm'

export default function TriggerFormSidebar({ onSidebarClose, onSubmit, title, initialFormData }) {
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

  const handleSubmit = (data) => {
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
