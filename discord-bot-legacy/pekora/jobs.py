import asyncio
from pekora.youtube import notifications_service
import pekora


class Jobs:

    tasks = []

    def __init__(self, loop=None):
        self.loop = loop

        if self.loop is None:
            self.loop = asyncio.get_event_loop()

    def set_interval(self, delay_seconds):
        def set_interval_decorator(callback):
            async def function_wrapper():
                while True:
                    callback()
                    await asyncio.sleep(delay_seconds)

            self.tasks.append({
                'name': callback.__name__,
                'task': self.loop.create_task(function_wrapper())
            })

            return function_wrapper

        return set_interval_decorator


def start_jobs(loop=None):
    job = Jobs(loop)

    @job.set_interval((24 * 60 * 60))
    def feed_subscribe():
        notifications_service.subscribe(pekora.HTTP_BASE_URL + '/webhook/pekora/feed')
