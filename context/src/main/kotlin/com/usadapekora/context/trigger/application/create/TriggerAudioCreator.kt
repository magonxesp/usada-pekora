package com.usadapekora.context.trigger.application.create

import com.usadapekora.context.shared.domain.file.UploadedFileWriter
import com.usadapekora.context.trigger.domain.TriggerAudioRepository

class TriggerAudioCreator(private val repository: TriggerAudioRepository, private val writer: UploadedFileWriter) {

    fun create(request: TriggerAudioCreateRequest) {
        TODO("Not yet implemented")
    }

}
