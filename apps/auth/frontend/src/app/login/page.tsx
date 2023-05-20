"use client"
import { Button, MainContainer } from '@usada-pekora/shared-ui'
import { redirect } from 'next/navigation'

const providers = {
  discord: "discord"
}

export default function Login() {

  const authorize = (provider: string) => {
    fetch(`http://localhost:8081/api/v1/oauth/provider/authorize/${provider}`)
      .then(response => response.blob())
      .then(content => content.text())
      .then(url => window.location.href = url)
  }

  return (
    <MainContainer>
      <>
        <h1>Login</h1>
        <Button onClick={() => authorize(providers.discord)}>Iniciar sesion con discord</Button>
      </>
    </MainContainer>
  )
}
