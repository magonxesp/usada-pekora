import { Trigger } from '../../../../modules/trigger/domain/trigger'
import Button from '../../../shared/form/Button'
import { PencilSquareIcon, TrashIcon } from '@heroicons/react/24/outline'

interface TriggerCardProps {
  trigger: Trigger
}

export default function TriggerCard(props: TriggerCardProps) {
  let type = 'Sin respuesta'
  const { trigger } = props

  if (trigger.outputAudio) {

  }

  return (
    <div className="overflow-hidden bg-white shadow sm:rounded-lg mb-3">
      <div className="px-4 py-5 sm:px-6 flex justify-between">
        <div>
          <h3 className="text-lg font-medium leading-6 text-gray-900">{props.trigger.title}</h3>
          <p className="mt-1 max-w-2xl text-sm text-gray-500">{props.trigger.uuid}</p>
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
