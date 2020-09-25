import asyncio
import pekora
from pekora.bot import bot
from pekora.http import http


main_loop = asyncio.get_event_loop()
bot.loop = main_loop
main_loop.create_task(http.run_task('0.0.0.0', 5000))
bot.run(pekora.TOKEN)
