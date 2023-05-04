import Button from '../../../common/form/Button/Button'
import { PencilSquareIcon, TrashIcon } from '@heroicons/react/24/outline'
import Link from 'next/link'
import { useDeleteTrigger } from '../../../../shared/hooks/trigger'

import { Trigger } from '../../../../shared/api/backend/trigger/trigger'

interface TriggerCardProps {
  trigger: Trigger
}

export default function TriggerCard({ trigger }: TriggerCardProps) {
  const deleteTrigger = useDeleteTrigger()

  return (
    <div className='overflow-hidden bg-white shadow sm:rounded-lg mb-3 px-4 py-5 sm:px-6 flex justify-between'>
      <div className='space-y-2'>
        <h3 className='text-lg font-medium leading-6 text-gray-900'>{trigger.title ?? 'Sin título'}</h3>
      </div>
      <div className='flex items-center'>
        <Link href={`/trigger/edit/${trigger.id}`} >
          <button className="bg-primary">
            <PencilSquareIcon className='w-5' />
          </button>
        </Link>
        <Button onClick={() => deleteTrigger(trigger)}>
          <TrashIcon className='w-5' />
        </Button>
      </div>
    </div>
  )
}
