import { Trigger } from '../../../../modules/trigger/domain/trigger'
import Button from '../../../shared/form/Button'
import { PencilSquareIcon, TrashIcon } from '@heroicons/react/24/outline'
import TriggerFeatureList from '../feature-list/TriggerFeatureList'

interface TriggerCardProps {
  trigger: Trigger
}

export default function TriggerCard(props: TriggerCardProps) {
  const { trigger } = props
  const features = trigger.features()

  return (
    <div className="overflow-hidden bg-white shadow sm:rounded-lg mb-3">
      <div className="px-4 py-5 sm:px-6 flex justify-between">
        <div className="space-y-2">
          <h3 className="text-lg font-medium leading-6 text-gray-900">{trigger.title ?? 'Sin título'}</h3>
          {(features.length > 0) ? (
            <TriggerFeatureList features={features} />
          ) : ''}
        </div>
        <div className="flex items-center">
          <Button>
            <PencilSquareIcon className="w-5" />
          </Button>
          <Button>
            <TrashIcon className="w-5" />
          </Button>
        </div>
      </div>
    </div>
  )
}
