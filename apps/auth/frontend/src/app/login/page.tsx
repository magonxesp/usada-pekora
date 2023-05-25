"use client"
import { Button, MainContainer } from '@usada-pekora/shared-ui'
import { oAuthProviderAuthorize, oAuthProviders } from '../../helpers/backend'

export default function Login() {
  return (
    <MainContainer>
      <>
        <h1>Login</h1>
        <Button onClick={() => oAuthProviderAuthorize(oAuthProviders.discord)}>Iniciar sesion con discord</Button>
      </>
    </MainContainer>
  )
}
