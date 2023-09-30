import { CardList } from '@usada-pekora/shared-ui'
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
