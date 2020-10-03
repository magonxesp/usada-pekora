import asyncio
import pekora.entitys
import pekora
from pekora.bot import bot
from pekora.http import http
import os


def main():
    if os.path.exists(pekora.DB_DIR) is False:
        os.mkdir(pekora.DB_DIR)

    pekora.entitys.db.bind(
        provider='sqlite',
        filename=os.path.join(pekora.DB_DIR, 'database.sqlite'),
        create_db=True
    )

    pekora.entitys.db.generate_mapping(create_tables=True)

    main_loop = asyncio.get_event_loop()
    bot.loop = main_loop
    main_loop.create_task(http.run_task('0.0.0.0', 3000))
    bot.run(pekora.TOKEN)


if __name__ == '__main__':
    main()
