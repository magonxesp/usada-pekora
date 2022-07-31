package es.magonxesp.pekorabot.modules.trigger.infraestructure.strapi.trigger

import kotlinx.serialization.Serializable

@Serializable
class TriggerList(val data: Array<Trigger>)
