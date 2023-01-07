import { GetServerSideProps, InferGetServerSidePropsType } from 'next'
import GuildTriggersView from '../../../components/views/guild-triggers-view/GuildTriggersView'
import { Trigger, TriggerPrimitives } from '../../../shared/domain/trigger'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/router'
import Sidebar from '../../../components/shared/sidebar/Sidebar'
import TriggerForm from '../../../components/domain/trigger/form/TriggerForm'
import { useIntl } from 'react-intl'
import { triggerFinder } from '../../../shared/application-services'
import { toast } from '../../../shared/infraestructure/helpers'

export const getServerSideProps: GetServerSideProps<{ trigger: TriggerPrimitives }> = async (context) => {
  const { uuid } = context.query
  const trigger = await triggerFinder().findById(uuid as string)

  return {
    props: {
      trigger: trigger.toPrimitives()
    }
  }
}

const TriggerEdit = ({ trigger }: InferGetServerSidePropsType<typeof getServerSideProps>) => {
  const [isOpened, setIsOpened] = useState(false)
  const router = useRouter()
  const intl = useIntl()

  useEffect(() => {
    setTimeout(() => setIsOpened(true), 100)
  }, [])

  const closeSidebar = () => {
    setIsOpened(false)
    setTimeout(async () => await router.push("/"), 500)
  }

  const updateTrigger = (trigger: Trigger) => {
    try {
      throw "Ups"
    } catch (exception) {
      toast(intl.$t({ id: 'trigger.form.update.error' }), 'error')
    }
  }

  return (
    <>
      <GuildTriggersView />
      <Sidebar show={isOpened}>
        <Sidebar.Header onClose={closeSidebar}>
          <h2 className="heading-2 py-0">
            {intl.$t({ id: 'trigger.form.sidebar.edit.title' })}
          </h2>
        </Sidebar.Header>
        <Sidebar.Body>
          <TriggerForm trigger={Trigger.fromPrimitives(trigger)} onSubmit={updateTrigger} />
        </Sidebar.Body>
      </Sidebar>
    </>
  )
}

export default TriggerEdit
