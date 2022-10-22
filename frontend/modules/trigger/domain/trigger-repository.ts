import { Trigger } from './trigger'

export interface TriggerRepository {
  findId(uuid: string): Promise<Trigger>
  findByDiscordServerId(discordServerId: string): Promise<Trigger[]>
  save(trigger: Trigger): Promise<void>
  delete(trigger: Trigger): Promise<void>
}
