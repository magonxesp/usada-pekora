import { useDispatch } from 'react-redux'
import { useAppSelector } from '../../../hooks'
import { useEffect, useState } from 'react'
import { setTriggers } from '../../../store/slices/app-slice'
import { Trigger } from '../../../modules/trigger/domain/trigger'
import Button from '../../shared/form/Button'
import TriggerList from '../../domain/trigger/list/TriggerList'
import EmptyState from '../../shared/empty-state/EmptyState'

export default function TriggersView() {
  const dispatch = useDispatch();
  const triggers = useAppSelector((state) => state.app.triggers)
  const selectedGuild = useAppSelector((state) => state.app.selectedGuild)
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadingAnimationTimeout = setTimeout(() => setLoading(true), 100)

    fetch(`/api/trigger/guild-triggers?id=${selectedGuild}`)
      .then(response => response.json())
      .then(items => {
        clearTimeout(loadingAnimationTimeout)
        setLoading(false)
        dispatch(setTriggers(items.map((item: object) => Object.assign(new (Trigger as any)(), item))))
      })
  }, [selectedGuild])

  return (
    <>
      <div className="flex justify-between items-center">
        <h1 className="heading-1">Reacciones</h1>
        <Button color="primary">Añadir</Button>
      </div>
      {!loading && triggers.length > 0 ? (
        <TriggerList items={triggers} />
      ) : !loading && triggers.length == 0 ? (
        <EmptyState />
      ) : (
        <div className="space-y-5">
          {Array.from(Array(5).keys()).map(() => (
            <div>
              <div role="status" className="animate-pulse mb-2">
                <div className="w-full h-6 bg-gray-200 rounded dark:bg-gray-600"></div>
              </div>
              <div role="status" className="animate-pulse">
                <div className="w-9/12 h-6 bg-gray-200 rounded dark:bg-gray-600"></div>
              </div>
            </div>
          ))}
        </div>
      )}
    </>
  )
}
