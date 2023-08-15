package com.usadapekora.shared.domain

interface PersistenceTransaction {
    fun start()
    fun rollback()
    fun commit()
}
