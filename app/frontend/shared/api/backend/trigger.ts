import { backendUrl, headers } from './backend'
import axios, { toFormData } from 'axios'
import { Trigger, TriggerCompare } from '../../domain/trigger'

interface TriggerCreateRequest {
  id: string,
  input: string,
  compare: string,
  outputText?: string,
  discordGuildId: string
}

interface TriggerAudioCreateRequest {
  id: string
  file: File|Buffer
  triggerId: string
  guildId: string
}

export async function createTriggerAudio(audio: TriggerAudioCreateRequest) {
  await axios.post(backendUrl("/api/v1/trigger/audio"), toFormData(audio), {
    headers: headers("multipart/form-data")
  })
}

export async function createTrigger(trigger: TriggerCreateRequest) {
  await axios.post(backendUrl("/api/v1/trigger"), trigger, {
    headers: headers()
  })
}

export async function fetchGuildTriggers(guildId: string): Promise<Trigger[]> {
  return [
    new Trigger({
      uuid: "fee1ff15-67fc-4cb6-b464-9a567549d0fb",
      title: "Lorem fistrum está la cosa muy malar caballo",
      compare: TriggerCompare.CONTAINS,
      discordServerId: "222354445645",
      input: "chiquito",
      outputText: null,
    }),
    new Trigger({
      uuid: "4af2bd18-6b33-4000-bddc-bf8ae7662c51",
      title: "Lorem fistrum está la cosa muy malar caballo",
      compare: TriggerCompare.PATTERN,
      discordServerId: "222354445645",
      input: "chiquito",
      outputText: "Lorem fistrum está la cosa muy malar caballo"
    }),
    new Trigger({
      uuid: "d360c313-a5d6-440b-8274-b48a3c98065e",
      title: "Lorem fistrum está la cosa muy malar caballo",
      compare: TriggerCompare.CONTAINS,
      discordServerId: "222354445645",
      input: "chiquito",
      outputText: "Lorem fistrum está la cosa muy malar caballo",
    }),
    new Trigger({
      uuid: "8df821bc-e77b-4573-87ce-a163494a53d1",
      title: "Lorem fistrum está la cosa muy malar caballo",
      compare: TriggerCompare.CONTAINS,
      discordServerId: "222354445645",
      input: "chiquito",
      outputText: null,
    }),
  ]
}
