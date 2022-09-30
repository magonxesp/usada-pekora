import type { GetServerSideProps, GetServerSidePropsResult, InferGetServerSidePropsType, NextPage } from 'next'
import Card from '../components/shared/card/Card'
import Select from '../components/shared/form/Select'
import { SelectOption } from '../modules/shared/domain/form/select-option'
import { DiscordApiGuildRepository } from '../modules/guild/infraestructure/persistence/discord-api-guild-repository'
import { GuildFinder } from '../modules/guild/application/guild-finder'
import { serverSession } from '../modules/shared/infraestructure/auth/session'
import { useState } from 'react'
import { Trigger } from '../modules/trigger/domain/trigger'


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
          .then(json => setTriggers(json as Trigger[]))
      }} />
      <h1>Triggers</h1>
      {triggers.length > 0 ? triggers.map(trigger => (
        <p key={trigger.uuid}>{trigger.input}</p>
      )) : (
        <p>Este servidor de discord no tiene triggers</p>
      )}
      <Card />
    </div>
  )
}

export default Home
