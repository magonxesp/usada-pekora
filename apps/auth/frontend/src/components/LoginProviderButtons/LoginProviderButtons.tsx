import { oAuthProviderAuthorize, oAuthProviders } from '../../helpers/backend'
import { Button } from '@usada-pekora/shared-ui'

export function LoginProviderButtons() {
  return (
    <div>
      <Button onClick={() => oAuthProviderAuthorize(oAuthProviders.discord)}>Iniciar sesion con discord</Button>
    </div>
  )
}
