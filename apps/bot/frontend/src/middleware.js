import { NextResponse } from 'next/server'
import { fetchCurrentUser } from './modules/user/fetch'

// This function can be marked `async` if using `await` inside
export async function middleware(request) {
  if (!request.cookies.has(process.env.NEXT_PUBLIC_SESSION_COOKIE_NAME)) {
    return NextResponse.redirect(new URL('/', process.env.NEXT_PUBLIC_AUTH_FRONTEND_BASE_URL))
  }

  const user = await fetchCurrentUser(request.cookies.get(process.env.NEXT_PUBLIC_SESSION_COOKIE_NAME)?.value)

  if (!user) {
    return NextResponse.redirect(new URL('/login', process.env.NEXT_PUBLIC_AUTH_FRONTEND_BASE_URL))
  }

  return NextResponse.next();
}

// See "Matching Paths" below to learn more
export const config = {
  matcher: ['/', '/trigger', '/trigger/edit/:id', '/trigger/create'],
}
