from discord.ext import commands
import discord
import os
import pekora


COMMAND_PREFIX = "peko!"
bot = commands.Bot(command_prefix=COMMAND_PREFIX)


async def play_audio(author: discord.Member, audio_file_name: str):
    if author.voice:
        channel: discord.VoiceChannel = author.voice.channel
        client: discord.VoiceClient = await channel.connect()

        client.play(discord.FFmpegPCMAudio(os.path.join(pekora.AUDIO_DIR, audio_file_name)))

        while client.is_playing():
            pass

        await client.disconnect()


@bot.event
async def on_message(message: discord.Message):
    message_text: str = message.content
    channel: discord.abc.Messageable = message.channel

    if message.author == bot.user:
        return

    if message_text.startswith(COMMAND_PREFIX):
        await bot.process_commands(message)
        return

    if "peko" in message_text:
        await channel.send("It’s a me, Pekora!")
        await play_audio(message.author, "Its_me_pekora.mp3")
        return

    if "hahaha" in message_text or "jajaja" in message_text:
        await channel.send("HA↗HA↘HA↗HA↘HA↗HA↘")
        await play_audio(message.author, "hahahahaha.mp3")
        return


@bot.command()
async def hi(context: commands.Context):
    """It’s a me, Pekora!"""
    author: discord.Member = context.message.author
    await context.send("It’s a me, Pekora!")
    await play_audio(author, "Its_me_pekora.mp3")


@bot.command()
async def hahaha(context: commands.Context):
    """HA↗HA↘HA↗HA↘HA↗HA↘"""
    author: discord.Member = context.message.author
    await context.send("HA↗HA↘HA↗HA↘HA↗HA↘")
    await play_audio(author, "hahahahaha.mp3")
