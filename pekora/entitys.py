from pony.orm import Database, Required


db = Database()


class TextChannel(db.Entity):

    serverId = Required(str)
    channelId = Required(str)


class YoutubeNotification(db.Entity):

    channel_id = Required(str)
    message_id = Required(str)
    video_id = Required(str)
    create_date = Required(int)
