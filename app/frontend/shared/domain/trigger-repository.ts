import { Trigger, TriggerDiscordServerId, TriggerId } from './trigger'

export interface TriggerRepository {
  findById(uuid: TriggerId): Promise<Trigger>
  findByDiscordServerId(discordServerId: TriggerDiscordServerId): Promise<Trigger[]>
  save(trigger: Trigger): Promise<void>
  delete(trigger: Trigger): Promise<void>
}
