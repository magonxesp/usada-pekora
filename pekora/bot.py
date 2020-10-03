from discord.ext import commands
import discord
import pekora.response
import pekora
from pony.orm import db_session, select
from pekora.entitys import TextChannel
from pekora.youtube import YoutubeVideo


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


def channel_exists(channel_id: str, guild_id: str):
    text_channel = select(t for t in TextChannel if t.channelId == channel_id and t.serverId == guild_id)
    return len(text_channel) > 0


async def enable_feed(context: commands.Context):
    channel_id = str(context.channel.id)
    guild_id = str(context.guild.id)

    if channel_exists(channel_id, guild_id) is False:
        with db_session:
            try:
                TextChannel(
                    serverId=guild_id,
                    channelId=channel_id
                )

                await context.send("Notificaciones de youtube activadas")
            except Exception as e:
                await context.send("Error al activar las notificaciones de youtube")
                pekora.LOGGER.warning(e)


async def disable_feed(context: commands.Context):
    channel_id = str(context.channel.id)
    guild_id = str(context.guild.id)
    text_channel: TextChannel = select(t for t in TextChannel if t.channelId == channel_id and t.serverId == guild_id).first()

    if text_channel:
        try:
            text_channel.delete()
            await context.send("Notificaciones de youtube desactivadas")
        except Exception as e:
            await context.send("Error al desactivar las notificaciones de youtube")
            pekora.LOGGER.warning(e)


@bot.command()
async def feed(context: commands.Context, arg: str):
    if arg == "on":
        await enable_feed(context)
    elif arg == "off":
        await disable_feed(context)


async def send_youtube_notification(video: YoutubeVideo):
    text_channels = select(t for t in TextChannel)

    for text_channel in text_channels:
        text_channel = bot.get_channel(int(text_channel.channelId))
        await text_channel.send("Estoy en directo: **{title}** - {url}".format(
            title=video.get_title(),
            url=video.get_url()
        ))
