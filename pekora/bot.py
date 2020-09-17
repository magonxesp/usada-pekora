from discord.ext import commands
import discord
import pekora.response


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
