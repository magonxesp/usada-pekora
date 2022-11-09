import { NextPage } from 'next'
import TriggerForm from '../../components/domain/trigger/form/TriggerForm'
import { Trigger } from '../../modules/trigger/domain/trigger'

const NewTrigger: NextPage = () => {
  return (
    <>
      <TriggerForm trigger={Trigger.empty()} />
    </>
  )
}

export default NewTrigger
