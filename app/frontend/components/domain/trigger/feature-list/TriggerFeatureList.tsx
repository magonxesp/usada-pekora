import { TriggerFeature } from '../../../../shared/trigger/trigger'
import { ChatBubbleLeftIcon, SpeakerWaveIcon } from '@heroicons/react/24/solid'

interface TriggerFeatureListProps {
  features: Array<TriggerFeature>
  className?: string
}

const iconClassName = "ml-1 h-3"
const listItemClassName = "flex items-center bg-secondary rounded p-1 text-xs"

const features = (key: number) => ({
  [TriggerFeature.AUDIO_RESPONSE]: (
    <li key={key} className={listItemClassName}>Audio<SpeakerWaveIcon className={iconClassName} /></li>
  ),
  [TriggerFeature.TEXT_RESPONSE]: (
    <li key={key} className={listItemClassName}><span>Texto</span> <ChatBubbleLeftIcon className={iconClassName} /></li>
  ),
})

export default function TriggerFeatureList(props: TriggerFeatureListProps) {
  return (
    <ul className={`flex justify-start space-x-1.5 ${props.className ?? ''}`}>
      {props.features.map((feature, index) => features(index)[feature])}
    </ul>
  )
}
