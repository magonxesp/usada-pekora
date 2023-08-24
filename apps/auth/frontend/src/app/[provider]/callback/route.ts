import { fetchAuthToken, oAuthProviderAuthorizeCode } from '../../../helpers/backend'
import { NextRequest, NextResponse } from 'next/server'

interface Params {
  params: {
    provider: string
  }
}

export async function GET(request: NextRequest, { params }: Params) {
  const codeParameter = request.nextUrl.searchParams.get("code")

  if (!codeParameter) {
    throw Error("The code parameter is undefined")
  }

  const code = await oAuthProviderAuthorizeCode(params.provider, codeParameter)

  if (!process.env.NEXT_PUBLIC_LOGIN_REDIRECT_URL) {
    throw Error("The LOGIN_REDIRECT_URL env variable is undefined")
  }

  const token = await fetchAuthToken(code)
  const response = NextResponse.redirect(process.env.NEXT_PUBLIC_LOGIN_REDIRECT_URL);

  response.cookies.set({
    name: process.env.NEXT_PUBLIC_SESSION_COOKIE_NAME as string,
    value: token.accessToken,
    path: '/',
    httpOnly: false,
    expires: new Date(token.expiresAt),
    maxAge: token.expiresAt,
    domain: process.env.NEXT_PUBLIC_SESSION_COOKIE_DOMAIN as string
  })

  return response
}
