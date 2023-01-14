package com.usadapekora.context.domain.user

interface UserRepository {
    /**
     * Find user by id
     *
     * @throws UserException.NotFound in case the user is not found
     */
    fun find(id: String): User

    /**
     * Find user by discord id
     *
     * @throws UserException.NotFound in case the user is not found
     */
    fun findByDiscordId(discordId: String): User

    /**
     * Save the user to the database
     */
    fun save(entity: User)

    /**
     * Delete the user from the database
     */
    fun delete(entity: User)
}
