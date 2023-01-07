import { TriggerAudioRepository } from '../domain/trigger-audio-repository'
import { TriggerAudioId } from '../domain/trigger-audio'

export interface TriggerAudioCreateRequest {
  uuid: TriggerAudioId
  file: File
}

export class TriggerAudioCreator {

  constructor(private repository: TriggerAudioRepository) { }

  create(request: TriggerAudioCreateRequest) {

  }

}
