import { Trigger } from '../../../../modules/trigger/domain/trigger'
import { FormEvent } from 'react'

interface TriggerFormProps {
  trigger: Trigger
}

function saveTrigger() {

}

export default function TriggerForm(props: TriggerFormProps) {
  <form onSubmit={saveTrigger}>
    <input type="text" name="title" />
    <input type="text" name="output_text" />
  </form>
}
