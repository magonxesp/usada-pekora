import { Trigger } from './trigger'

export interface TriggerRepository {
  findId(uuid: string): Promise<Trigger>
  findByDiscordServerId(discordServerId: string): Promise<Trigger[]>
  save(trigger: Trigger): void
}
