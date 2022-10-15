import type { GetServerSideProps, GetServerSidePropsResult, InferGetServerSidePropsType, NextPage } from 'next'
import Select from '../components/shared/form/Select'
import { SelectOption } from '../modules/shared/domain/form/select-option'
import { DiscordApiGuildRepository } from '../modules/guild/infraestructure/persistence/discord-api-guild-repository'
import { GuildFinder } from '../modules/guild/application/guild-finder'
import { serverSession } from '../modules/shared/infraestructure/auth/session'
import { useState } from 'react'
import { Trigger } from '../modules/trigger/domain/trigger'
import TriggerList from '../components/domain/trigger/list/TriggerList'
import EmptyState from '../components/shared/empty-state/EmptyState'
import Button from '../components/shared/form/Button'


export const getServerSideProps: GetServerSideProps = async (context): Promise<GetServerSidePropsResult<any>> => {
  const session = await serverSession(context.req, context.res)
  // @ts-ignore
  const finder = new GuildFinder(new DiscordApiGuildRepository(session.accessToken))
  const guilds = await finder.userGuilds()

  return {
    props: {
      guilds: guilds.map((guild): SelectOption => ({
        label: guild.name,
        value: guild.id,
        labelIcon: guild.icon,
      }))
    }
  }
}

const Home: NextPage = ({ guilds }: InferGetServerSidePropsType<typeof getServerSideProps>) => {
  const [triggers, setTriggers] = useState<Trigger[]>([])

  return (
    <div>
      <Select options={guilds} onChange={(option) => {
        fetch(`/api/trigger/guild-triggers?id=${option.value}`)
          .then(response => response.json())
          .then(items => setTriggers(items.map((item: object) => Object.assign(new (Trigger as any)(), item))))
      }} />
      <div className="py-5">
        <div className="flex justify-between items-center">
          <h1 className="heading-1">Reacciones</h1>
          <Button color="primary">Añadir</Button>
        </div>
        {triggers.length > 0 ? (
          <TriggerList items={triggers} />
        ) : (
          <EmptyState />
        )}
      </div>
    </div>
  )
}

export default Home
