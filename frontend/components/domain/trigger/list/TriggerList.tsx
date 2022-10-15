import { Trigger } from '../../../../modules/trigger/domain/trigger'
import TriggerCard from '../card/TriggerCard'

interface TriggerListProps {
  items: Array<Trigger>
}

export default function TriggerList(props: TriggerListProps) {
  return (
    <div>
      {props.items.map((trigger, index) => (
        <TriggerCard key={index} trigger={trigger} />
      ))}
    </div>
  )
}
