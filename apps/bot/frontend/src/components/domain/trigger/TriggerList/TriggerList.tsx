import { CardList } from '@usada-pekora/shared-ui'
import TriggerCard from '../TriggerCard/TriggerCard'
import { Trigger } from '../../../../modules/trigger/trigger'

interface TriggerListProps {
  items: Array<Trigger>
}

export default function TriggerList({ items }: TriggerListProps) {
  return (
    <CardList>
      <>
        {items.map((trigger, index) => (
          <TriggerCard key={index} trigger={trigger} />
        ))}
      </>
    </CardList>
  )
}
