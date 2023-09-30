import { CardList } from '@usada-pekora/ui/components'
import TriggerCard from '../TriggerCard/TriggerCard'

export default function TriggerList({ items }) {
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
