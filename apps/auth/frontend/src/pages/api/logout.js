import { deleteCookie } from 'cookies-next';

export default async function handler(req, res) {
  deleteCookie(process.env.NEXT_PUBLIC_SESSION_COOKIE_NAME, {
    req,
    res,
    path: '/',
    domain: process.env.NEXT_PUBLIC_SESSION_COOKIE_DOMAIN
  })

  res.writeHead(302, { Location: '/login' });
  res.end();
}
