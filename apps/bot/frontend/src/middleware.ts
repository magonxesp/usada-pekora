import type { NextRequest } from 'next/server'
import { NextResponse } from 'next/server'
import { fetchCurrentUser } from './modules/user/fetch'

// This function can be marked `async` if using `await` inside
export async function middleware(request: NextRequest) {
  if (!request.cookies.has(process.env.NEXT_PUBLIC_SESSION_COOKIE_NAME as string)) {
    return NextResponse.redirect(new URL('/', process.env.NEXT_PUBLIC_AUTH_FRONTEND_BASE_URL))
  }

  const user = await fetchCurrentUser(request)

  if (!user) {
    return NextResponse.redirect(new URL('/', process.env.NEXT_PUBLIC_AUTH_FRONTEND_BASE_URL))
  }

  return NextResponse.next();
}

// See "Matching Paths" below to learn more
export const config = {
  matcher: ['/', '/trigger', '/trigger/edit/:id', '/trigger/create'],
}
