import { TriggerRepository } from '../domain/trigger-repository'
import { Trigger } from '../domain/trigger'

export class TriggerCreator {

  constructor(private repository: TriggerRepository) { }

  async create(trigger: Trigger) {
    await this.repository.save(trigger)
  }
  
}
