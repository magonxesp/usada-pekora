export default function TriggerListSkeleton() {
  return (
    <div className="space-y-5">
      {Array.from(Array(5).keys()).map((_, index) => (
        <div key={index}>
          <div role="status" className="animate-pulse mb-2">
            <div className="w-full h-6 bg-gray-200 rounded dark:bg-gray-600"></div>
          </div>
          <div role="status" className="animate-pulse">
            <div className="w-9/12 h-6 bg-gray-200 rounded dark:bg-gray-600"></div>
          </div>
        </div>
      ))}
    </div>
  )
}
