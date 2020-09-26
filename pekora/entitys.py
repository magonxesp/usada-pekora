from pony.orm import Database, Required


db = Database()


class TextChannel(db.Entity):

    serverId = Required(str)
    channelId = Required(str)
