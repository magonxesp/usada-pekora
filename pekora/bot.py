from discord.ext import commands
import discord
import os
import pekora


bot = commands.Bot(command_prefix="peko!")


def play_its_me_pekora(client: discord.VoiceClient):
    client.play(
        discord.FFmpegPCMAudio(os.path.join(pekora.AUDIO_DIR, 'Its_me_pekora.mp3')),
        after=lambda e: client.disconnect()
    )


@bot.command()
async def hi(context: commands.Context):
    author: discord.Member = context.message.author
    channel: discord.VoiceChannel = author.voice.channel

    if channel:
        voice_channel: discord.VoiceClient = await channel.connect()
        play_its_me_pekora(voice_channel)


