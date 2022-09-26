import type { GetServerSideProps, GetServerSidePropsResult, InferGetServerSidePropsType, NextPage } from 'next'
import Card from '../components/shared/card/Card'
import Select from '../components/shared/form/Select'
import { SelectOption } from '../modules/shared/domain/form/select-option'
import { DiscordApiGuildRepository } from '../modules/guild/infraestructure/persistence/discord-api-guild-repository'
import { GuildFinder } from '../modules/guild/application/guild-finder'
import { useServerSession } from '../modules/shared/infraestructure/auth/session'


export const getServerSideProps: GetServerSideProps = async (context): Promise<GetServerSidePropsResult<any>> => {
  const session = await useServerSession(context.req, context.res)
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

interface HomeProps {
  guilds: SelectOption[]
}

const Home: NextPage = ({ guilds }: InferGetServerSidePropsType<typeof getServerSideProps>) => {
  return (
    <div>
      <Select options={guilds} />
      <Card />
    </div>
  )
}

export default Home
