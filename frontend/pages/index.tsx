import type { GetServerSideProps, GetServerSidePropsResult, InferGetServerSidePropsType, NextPage } from 'next'
import Card from '../components/shared/card/Card'
import Select from '../components/shared/form/Select'
import { SelectOption } from '../modules/shared/form/select-option'
import { userGuilds } from '../modules/discord/guild'
import { useServerSession } from '../modules/shared/auth/session'


export const getServerSideProps: GetServerSideProps = async (context): Promise<GetServerSidePropsResult<any>> => {
  const session = await useServerSession(context.req, context.res)
  console.log(session)
  // @ts-ignore
  const guilds = await userGuilds(session.accessToken)



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
