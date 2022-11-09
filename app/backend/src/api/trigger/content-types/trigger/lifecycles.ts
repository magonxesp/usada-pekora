import { v4 as uuidv4 } from "uuid";

export default {
  beforeCreate(event) {
    const { data } = event.params;

    if (!data.uuid) {
      event.params.data.uuid = uuidv4();
    }
  }
}
