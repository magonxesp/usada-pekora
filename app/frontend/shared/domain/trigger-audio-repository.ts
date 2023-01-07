import { TriggerAudio, TriggerAudioId } from './trigger-audio'


export interface TriggerAudioRepository {
  findById(uuid: TriggerAudioId): Promise<TriggerAudio>
  save(audio: TriggerAudio): Promise<void>
}
