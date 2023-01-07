import { TriggerRepository } from '../domain/trigger-repository'
import { Trigger, TriggerId } from '../domain/trigger'

export class TriggerFinder {

  constructor(private repository: TriggerRepository) { }

  async findByDiscordServerId(discordServerId: string): Promise<Trigger[]> {
    return await this.repository.findByDiscordServerId(discordServerId)
  }

  async findById(uuid: TriggerId): Promise<Trigger> {
    return await this.repository.findById(uuid)
  }
}
