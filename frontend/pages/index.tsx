import UserGuildSelect from '../components/domain/guild/select/UserGuildSelect'
import { NextPage } from 'next'
import { useEffect } from 'react'
import { setCurrentGuild } from '../store/slices/app-slice'
import { useDispatch } from 'react-redux'
import TriggersView from '../components/views/triggers/TriggersView'


const Home: NextPage = () => {
  const dispatch = useDispatch();

  useEffect(() => {
    let guildId

    if ((guildId = sessionStorage.getItem('current_selected_guild')) != null) {
      dispatch(setCurrentGuild(guildId))
    }
  },[])

  return (
    <>
      <UserGuildSelect />
      <div className="py-5">
        <TriggersView />
      </div>
    </>
  )
}

export default Home
