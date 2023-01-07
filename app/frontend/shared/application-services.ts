import { FakeTriggerRepository } from './infraestructure/persistence/fake/fake-trigger-repository'
import { TriggerFinder } from './application/trigger-finder'
import { GuildFinder } from './application/guild-finder'
import { DiscordApiGuildRepository } from './infraestructure/persistence/api/discord-api-guild-repository'
import { TriggerUpdater } from './application/trigger-updater'
import { TriggerCreator } from './application/trigger-creator'

const services: { [name: string]: any } = {}

const triggerRepository = (): FakeTriggerRepository => services["trigger_repository"] ?? (services["trigger_repository"] = new FakeTriggerRepository())
const guildRepository = (bearerToken: string): DiscordApiGuildRepository => services["guild_repository"] ?? (services["guild_repository"] = new DiscordApiGuildRepository(bearerToken))
export const triggerFinder = (): TriggerFinder => services["trigger_finder"] ?? (services["trigger_finder"] = new TriggerFinder(triggerRepository()))
export const triggerUpdater = (): TriggerUpdater => services["trigger_updater"] ?? (services["trigger_updater"] = new TriggerUpdater(triggerRepository()))
export const triggerCreator = (): TriggerCreator => services["trigger_creator"] ?? (services["trigger_creator"] = new TriggerCreator(triggerRepository()))
export const guildFinder = (bearerToken: string): GuildFinder => services["guild_finder"] ?? (services["guild_finder"] = new GuildFinder(guildRepository(bearerToken)))
