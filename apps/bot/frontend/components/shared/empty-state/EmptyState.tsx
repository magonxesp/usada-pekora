import Image from 'next/future/image'
import image from './empty-image.jpg'

interface EmptyStateProps {
  message?: string
}

export default function EmptyState(props: EmptyStateProps) {
  return (
    <div className="flex flex-col items-center p-10 border-dashed border-4 border-gray-300 rounded-3xl">
      <Image src={image} alt="Empty state image" className="w-40" />
      <p className="bold text-lg mt-4">{props.message ?? 'Esto esta vacio y lo sabes'}</p>
    </div>
  )
}
