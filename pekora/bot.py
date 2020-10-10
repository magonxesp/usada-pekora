from typing import List

from discord.ext import commands
import discord
import pekora.response
import pekora
from pony.orm import db_session, select, commit
import pekora.entitys
from pekora.youtube import YoutubeVideo
from pekora.youtube import notifications_service
import time


COMMAND_PREFIX = "peko!"
bot = commands.Bot(command_prefix=COMMAND_PREFIX)


@bot.event
async def on_message(message: discord.Message):
    message_text: str = message.content

    if message.author == bot.user:
        return

    if message_text.startswith(COMMAND_PREFIX):
        await bot.process_commands(message)
        return

    processor = pekora.response.MessageResponse(message)
    processor.process_message()
    await processor.response_text()
    await processor.response_audio()


@bot.command()
async def live(context: commands.Context):
    youtube = pekora.youtube.Youtube(pekora.GOOGLE_API_KEY)
    video = youtube.get_streaming(pekora.CHANNEL_ID)

    if video:
        await context.send("Estoy en directo: **{title}** - {url}".format(
            title=video.get_title(),
            url=video.get_url()
        ))
    else:
        await context.send("Estoy offline")


@db_session
def channel_exists(channel_id: str, guild_id: str):
    text_channel = select(t for t in pekora.entitys.TextChannel if t.channelId == channel_id and t.serverId == guild_id)
    return len(text_channel) > 0


@db_session
async def enable_feed(context: commands.Context):
    channel_id = str(context.channel.id)
    guild_id = str(context.guild.id)

    if channel_exists(channel_id, guild_id) is False:
        notifications_service.subscribe(pekora.HTTP_BASE_URL + '/webhook/pekora/feed')

        try:
            pekora.entitys.TextChannel(
                serverId=guild_id,
                channelId=channel_id
            )

            message = "Notificaciones de youtube activadas"
        except Exception as e:
            message = "Error al activar las notificaciones de youtube"
            pekora.LOGGER.warning(e)

        commit()
        await context.send(message)


@db_session
async def disable_feed(context: commands.Context):
    channel_id = str(context.channel.id)
    guild_id = str(context.guild.id)
    text_channel: pekora.entitys.TextChannel = select(t for t in pekora.entitys.TextChannel if t.channelId == channel_id and t.serverId == guild_id).first()

    if text_channel:
        try:
            text_channel.delete()
            message = "Notificaciones de youtube desactivadas"
        except Exception as e:
            message = "Error al desactivar las notificaciones de youtube"
            pekora.LOGGER.warning(e)

        commit()
        await context.send(message)


@bot.command()
async def feed(context: commands.Context, arg: str):
    if arg == "on":
        await enable_feed(context)
    elif arg == "off":
        await disable_feed(context)


async def get_sended_video_notification(video_id: str) -> List[discord.Message]:
    messages = select(m for m in pekora.entitys.YoutubeNotification if m.video_id == video_id)
    messages_objects = []

    for message in messages:
        try:
            channel: discord.TextChannel = bot.get_channel(int(message.channel_id))
            commit()
            _message: discord.Message = await channel.fetch_message(int(message.message_id))
            messages_objects.append(_message)
        except Exception as e:
            pekora.LOGGER.warning(e)

    return messages_objects


@db_session
async def send_youtube_notification(video: YoutubeVideo):
    messages = await get_sended_video_notification(video.get_id())
    message_content = "**{title}** {url}".format(
        title=video.get_title(),
        url=video.get_url()
    )

    if len(messages) > 0:
        for message in messages:
            await message.edit(content=message_content)
    else:
        text_channels = select(t for t in pekora.entitys.TextChannel)

        for text_channel in text_channels:
            channel: discord.TextChannel = bot.get_channel(int(text_channel.channelId))
            commit()

            message: discord.Message = await channel.send(message_content)

            pekora.entitys.YoutubeNotification(
                channel_id=channel.id,
                message_id=message.id,
                video_id=video.get_id(),
                create_date=int(round(time.time() * 1000))
            )

            commit()
