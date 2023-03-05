import { Trigger } from '../../../../shared/domain/trigger'
import Button from '../../../shared/form/Button'
import { PencilSquareIcon, TrashIcon } from '@heroicons/react/24/outline'
import Link from 'next/link'
import { useDispatch } from 'react-redux'
import { showModal, closeModal } from '../../../../store/slices/app-slice'
import ConfirmModal from '../../../shared/modal/ConfirmModal'
import { useIntl } from 'react-intl'
import { useDeleteTrigger } from '../../../../shared/hooks/trigger'

interface TriggerCardProps {
  trigger: Trigger
}

export default function TriggerCard(props: TriggerCardProps) {
  const { trigger } = props
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
