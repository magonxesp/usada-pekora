import { TriggerFormData } from './form/trigger-form-data'
import { BackendClient } from '../helpers/backend'
import { v4 as uuidv4 } from 'uuid'
import { TriggerAudioFormData } from './form/trigger-audio-form-data'

export class TriggerClient extends BackendClient {

  private async audioCreateRequest(audio: TriggerAudioFormData) {
    return await fetch(this.backendUrl("/api/v1/trigger/audio"), {
      method: "POST",
      body: audio.toFormData(),
      headers: this.headers("multipart/form-data")
    })
  }

  private async createRequest(trigger: TriggerFormData) {
    return await fetch(this.backendUrl("/api/v1/trigger"), {
      method: "POST",
      body: JSON.stringify({
        id: trigger.uuid,
        input: trigger.input,
        compare: trigger.compare,
        outputText: trigger.outputText ?? undefined,
        discordGuildId: trigger.discordServerId
      }),
      headers: this.headers()
    })
  }

  async createTrigger(data: TriggerFormData) {
    if (data.outputAudio != null) {
      await this.audioCreateRequest(new TriggerAudioFormData(uuidv4(), data.outputAudio, data.uuid, data.discordServerId))
    }

    await this.createRequest(data)
  }
}

