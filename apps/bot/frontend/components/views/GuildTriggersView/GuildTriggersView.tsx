import { useDispatch } from 'react-redux'
import { useAppSelector } from '../../../modules/shared/hooks'
import { useEffect, useState } from 'react'
import { setCurrentGuild } from '../../../store/slices/app-slice'
import TriggerList from '../../domain/trigger/TriggerList/TriggerList'
import EmptyState from '../../common/states/EmptyState/EmptyState'
import Link from 'next/link'
import UserGuildSelect from '../../domain/guild/UserGuildSelect/UserGuildSelect'
import TriggerListSkeleton from '../../domain/trigger/TriggerListSkeleton/TriggerListSkeleton'
import { useSelectedGuild } from '../../../modules/guild/hooks'
import SectionHeading from '../../common/layout/SectionHeading/SectionHeading'
import Section from '../../common/layout/Section/Section'
import Button from '../../common/form/Button/Button'
import { useIntl } from 'react-intl'
import { useFetchTriggers } from '../../../modules/trigger/hooks'

export default function GuildTriggersView() {
  const dispatch = useDispatch();
  const triggers = useAppSelector((state) => state.app.triggers)
  const selectedGuild = useSelectedGuild()
  const [loading, setLoading] = useState(true);
  const fetchTriggers = useFetchTriggers()
  const intl = useIntl()

  useEffect(() => {
    let guildId

    if ((guildId = sessionStorage.getItem('current_selected_guild')) != null) {
      dispatch(setCurrentGuild({ id: guildId }))
    }
  },[])

  useEffect(() => {
    if (!selectedGuild) {
      return
    }

    const loadingAnimationTimeout = setTimeout(() => setLoading(true), 100)

    fetchTriggers().then(() => {
      clearTimeout(loadingAnimationTimeout)
      setLoading(false)
    })
  }, [selectedGuild])

  return (
    <>
      <Section>
        <UserGuildSelect />
      </Section>
      <Section>
        <>
          <SectionHeading>
            <>
              <h1>{intl.$t({ id: 'trigger.guild_list.title' })}</h1>
              <Link href="/trigger/create">
                <Button>{intl.$t({ id: 'trigger.guild_list.add' })}</Button>
              </Link>
            </>
          </SectionHeading>
          {!loading && triggers.length > 0 ? (
            <TriggerList items={triggers} />
          ) : !loading && triggers.length == 0 ? (
            <EmptyState message={intl.$t({ id: 'trigger.guild_list.empty' })} />
          ) : (
            <TriggerListSkeleton />
          )}
        </>
      </Section>
    </>
  )
}
