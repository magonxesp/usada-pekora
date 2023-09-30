import { v4 as uuidv4 } from 'uuid'

export const emptyTriggerResponseTextFormData = () => ({
  id: uuidv4(),
  content: '',
  type: 'text'
})

export function triggerTextResponseToFormData(text) {
  return {
    id: text.id,
    content: text.content,
    type: text.type
  }
}
