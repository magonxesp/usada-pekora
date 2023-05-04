import { useDispatch } from 'react-redux'
import { setTriggers } from '../../store/slices/app-slice'
import { useSelectedGuild } from './guilds'
import { fetchGuildTriggers } from '../api/backend/trigger/trigger'

export function useFetchTriggers() {
  const selectedGuildId = useSelectedGuild()
  const dispatch = useDispatch()

  return async () => {
    const triggers = await fetchGuildTriggers(selectedGuildId)
    dispatch(setTriggers(triggers))
  }
}
