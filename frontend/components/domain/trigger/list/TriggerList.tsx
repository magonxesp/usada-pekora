import { Trigger } from '../../../../modules/trigger/domain/trigger'
import TriggerCard from '../card/TriggerCard'

interface TriggerListProps {
  items: Array<Trigger>
}

export default function TriggerList(props: TriggerListProps) {
  return (
    <ul>
      {props.items.map((trigger) => (
        <li key={trigger.uuid}>
          <TriggerCard trigger={trigger} />
        </li>
      ))}
    </ul>
  )
}
