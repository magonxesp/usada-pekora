import NextAuth, { NextAuthOptions } from 'next-auth'
import DiscordProvider from 'next-auth/providers/discord'
import { env } from "../../../modules/shared/env";

export const authOptions: NextAuthOptions = {
  providers: [
    DiscordProvider({
      clientId: env('DISCORD_CLIENT_ID'),
      clientSecret: env('DISCORD_CLIENT_SECRET'),
      authorization: {
        params: {
          scope: 'identify guilds'
        }
      }
    })
  ],
  session: {
    maxAge: 604800
  },
  callbacks: {
    async session({ session, token }) {
      const defaultSession: any = session

      defaultSession.user.id = token.id;
      defaultSession.accessToken = token.accessToken;

      return defaultSession;
    },
    async jwt({ token, user, account }) {
      if (user) {
        token.id = user.id;
      }
      if (account) {
        token.accessToken = account.access_token;
      }
      return token;
    },
  }
}

export default NextAuth(authOptions)
