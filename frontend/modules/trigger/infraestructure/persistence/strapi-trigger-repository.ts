import { TriggerRepository } from '../../domain/trigger-repository'
import { Trigger } from '../../domain/trigger'
import { StrapiClient } from '../../../shared/infraestructure/strapi/strapi-client'
import { TriggerModel, triggerModelToAggregate } from '../strapi/trigger-model'
import { TriggerNotFoundError } from '../../domain/trigger-error'

export class StrapiTriggerRepository implements TriggerRepository {

  async findByDiscordServerId(discordServerId: string): Promise<Trigger[]> {
    const client = new StrapiClient()
    const response = await client.request<TriggerModel>('GET', `/triggers?filters[discord_server_id][$eq][0]=${discordServerId}&populate[0]=output_audio`)
    return response.data.map(triggerModelToAggregate)
  }

  async findId(uuid: string): Promise<Trigger> {
    const client = new StrapiClient()
    const response = await client.request<TriggerModel>('GET', `/triggers?filters[uuid][$eq][0]=${uuid}&populate[0]=output_audio`)
    const trigger = response.data.map(triggerModelToAggregate).shift()

    if (!trigger) {
      throw new TriggerNotFoundError(`trigger with uuid ${uuid} not found`)
    }

    return trigger
  }

  save(trigger: Trigger): void {
  }

}
