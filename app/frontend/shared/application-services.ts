import { FakeTriggerRepository } from './infraestructure/persistence/fake/fake-trigger-repository'
import { TriggerFinder } from './application/trigger-finder'
import { GuildFinder } from './application/guild-finder'
import { DiscordApiGuildRepository } from './infraestructure/persistence/api/discord-api-guild-repository'

const services: { [name: string]: any } = {}

const triggerRepository = (): FakeTriggerRepository => services["trigger_repository"] ?? (services["trigger_repository"] = new FakeTriggerRepository())
const guildRepository = (bearerToken: string): DiscordApiGuildRepository => services["guild_repository"] ?? (services["guild_repository"] = new DiscordApiGuildRepository(bearerToken))
export const triggerFinder = (): TriggerFinder => services["trigger_finder"] ?? (services["trigger_finder"] = new TriggerFinder(triggerRepository()))
export const guildFinder = (bearerToken: string): GuildFinder => services["guild_finder"] ?? (services["guild_finder"] = new GuildFinder(guildRepository(bearerToken)))
