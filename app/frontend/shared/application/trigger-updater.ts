import { TriggerRepository } from '../domain/trigger-repository'
import { Trigger } from '../domain/trigger'

export class TriggerUpdater {

  constructor(private repository: TriggerRepository) { }

  async update(trigger: Trigger) {
    await this.repository.save(trigger)
  }

}
