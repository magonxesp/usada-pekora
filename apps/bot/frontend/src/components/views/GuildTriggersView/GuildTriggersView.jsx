import { useEffect, useState } from 'react'
import TriggerList from '../../domain/trigger/TriggerList/TriggerList'
import { Button, EmptyState, Section, SectionHeading } from '@usada-pekora/shared-ui'
import Link from 'next/link'
import UserGuildSelect from '../../domain/guild/UserGuildSelect/UserGuildSelect'
import TriggerListSkeleton from '../../domain/trigger/TriggerListSkeleton/TriggerListSkeleton'
import { useSelectedGuild } from '../../../modules/guild/hooks'
import { useIntl } from 'react-intl'
import { useFetchTriggers, useGetTriggers } from '../../../modules/trigger/hooks'

export default function GuildTriggersView() {
  const triggers = useGetTriggers()
  const { selected: selectedGuild } = useSelectedGuild()
  const [loading, setLoading] = useState(true);
  const fetchTriggers = useFetchTriggers()
  const intl = useIntl()

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
