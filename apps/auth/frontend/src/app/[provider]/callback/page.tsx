import { redirect } from 'next/navigation'
import { oAuthProviderAuthorizeCode } from '../../../helpers/backend'

interface PageProps {
  params: {
    provider: string
  },
  searchParams: {
    [key: string]: string
  }
}

export default async function Callback({ params, searchParams }: PageProps) {
  const code = await oAuthProviderAuthorizeCode(params.provider, searchParams.code)

  if (!process.env.NEXT_PUBLIC_LOGIN_REDIRECT_URL) {
    throw Error("The LOGIN_REDIRECT_URL env variable is undefined")
  }

  // TODO: set cookie with the jwt token
  redirect(`${process.env.NEXT_PUBLIC_LOGIN_REDIRECT_URL}?code=${code}`)
}
