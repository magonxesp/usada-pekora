import TriggerCard from '../TriggerCard/TriggerCard'
import { Trigger } from '../../../../shared/api/backend/trigger/trigger'

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
