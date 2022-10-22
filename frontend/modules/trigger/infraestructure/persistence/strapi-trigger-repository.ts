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

  async save(trigger: Trigger): Promise<void> {
    const client = new StrapiClient()
    const collection = await client.request<TriggerModel>('GET', `/triggers?filters[uuid][$eq]=${trigger.uuid}`)
    const existing = collection.data.shift()

    const requestBody = {
      data: {
        input: trigger.input,
        compare: trigger.compare,
        output_text: trigger.outputText,
        output_audio: trigger.outputAudio,
        discord_server_id: trigger.discordServerId,
        uuid: trigger.uuid,
        title: trigger.title
      }
    }

    if (existing) {
      await client.request<TriggerModel>('PUT', `/triggers/${existing.id}`, JSON.stringify(requestBody))
    } else {
      await client.request<TriggerModel>('POST', '/triggers', JSON.stringify(requestBody))
    }
  }

  async delete(trigger: Trigger): Promise<void> {
    const client = new StrapiClient()
    const collection = await client.request<TriggerModel>('GET', `/triggers?filters[uuid][$eq]=${trigger.uuid}`)
    const existing = collection.data.shift()

    if (existing) {
      await client.request<TriggerModel>('DELETE', `/triggers/${existing.id}`)
    }
  }

}
