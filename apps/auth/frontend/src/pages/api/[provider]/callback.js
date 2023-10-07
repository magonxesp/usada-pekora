import { fetchAuthToken, oAuthProviderAuthorizeCode } from '../../../helpers/backend'
import { setCookie } from 'cookies-next';

export default async function handler(req, res) {
  const { code, provider } = req.query

  if (!code) {
    throw Error("The code parameter is undefined")
  }

  const authCode = await oAuthProviderAuthorizeCode(provider, code)

  if (!process.env.NEXT_PUBLIC_LOGIN_REDIRECT_URL) {
    throw Error("The LOGIN_REDIRECT_URL env variable is undefined")
  }

  const token = await fetchAuthToken(authCode)

  setCookie(process.env.NEXT_PUBLIC_SESSION_COOKIE_NAME, token.accessToken, {
    req,
    res,
    path: '/',
    httpOnly: false,
    expires: new Date(token.expiresAt),
    maxAge: token.expiresAt,
    domain: process.env.NEXT_PUBLIC_SESSION_COOKIE_DOMAIN
  })

  res.writeHead(302, { Location: process.env.NEXT_PUBLIC_LOGIN_REDIRECT_URL });
  res.end();
}
