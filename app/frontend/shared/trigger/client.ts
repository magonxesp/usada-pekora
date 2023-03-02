import { TriggerFormData } from './form/trigger-form-data'
import { BackendClient } from '../helpers/backend'
import { v4 as uuidv4 } from 'uuid'
import { TriggerAudioFormData } from './form/trigger-audio-form-data'
import axios from 'axios'

export class TriggerClient extends BackendClient {

  private async audioCreateRequest(audio: TriggerAudioFormData) {
    const formData = audio.toFormData()
    await axios.post(this.backendUrl("/api/v1/trigger/audio"), formData, {
      headers: this.headers("multipart/form-data")
    })
  }

  private async createRequest(trigger: TriggerFormData) {
    const body = {
      id: trigger.uuid,
      input: trigger.input,
      compare: trigger.compare,
      outputText: trigger.outputText ?? undefined,
      discordGuildId: trigger.discordServerId
    }

    await axios.post(this.backendUrl("/api/v1/trigger"), body, {
      headers: this.headers()
    })
  }

  async createTrigger(data: TriggerFormData) {
    if (data.outputAudio != null) {
      await this.audioCreateRequest(new TriggerAudioFormData({
        id: uuidv4(),
        triggerId: data.uuid,
        guildId: data.discordServerId,
        file: data.outputAudio
      }))
    }

    await this.createRequest(data)
  }
}

