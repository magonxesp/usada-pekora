import { unstable_getServerSession } from 'next-auth'
import { authOptions } from '../../../pages/api/auth/[...nextauth]'
import { IncomingMessage, ServerResponse } from 'http'

export type SessionIncomingMessage = IncomingMessage & {cookies: Partial<{[p: string]: string}>}

export async function serverSession(req: SessionIncomingMessage, res: ServerResponse<IncomingMessage>) {
  return await unstable_getServerSession(req, res, authOptions)
}
