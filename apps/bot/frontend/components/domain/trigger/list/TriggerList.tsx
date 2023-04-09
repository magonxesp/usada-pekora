import { Trigger } from '../../../../shared/domain/trigger'
import TriggerCard from '../card/TriggerCard'

interface TriggerListProps {
  items: Array<Trigger>
}

export default function TriggerList(props: TriggerListProps) {
  return (
    <ul>
      {props.items.map((trigger) => (
        <li key={trigger.id}>
          <TriggerCard trigger={trigger} />
        </li>
      ))}
    </ul>
  )
}