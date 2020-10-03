import dotenv
import os
import logging
import sys


# global package constants
ROOT = os.path.realpath(os.path.join(os.path.dirname(__file__), '..'))
PACKAGE_DIR = os.path.realpath(os.path.dirname(__file__))
AUDIO_DIR = os.path.join(ROOT, 'assets', 'audio')
DB_DIR = os.path.join(ROOT, 'db')
CHANNEL_ID = 'UC1DCedRgGHBdm81E1llLhOQ'

# logger
logging.basicConfig(stream=sys.stdout, level=logging.DEBUG)
LOGGER = logging.getLogger(__name__)
handler = logging.StreamHandler(sys.stdout)
LOGGER.addHandler(handler)

# .env file variables
dotenv.load_dotenv(dotenv_path=os.path.join(ROOT, '.env'))

TOKEN = os.getenv('TOKEN')
ENV = os.getenv('BOT_ENV')
GOOGLE_API_KEY = os.getenv('GOOGLE_API_KEY')
HTTP_BASE_URL = os.getenv('HTTP_BASE_URL')

if ENV == "dev" or ENV == "development":
    import pydevd_pycharm

    pydevd_pycharm.settrace(
        'host.docker.internal',
        port=5000,
        stdoutToServer=True,
        stderrToServer=True
    )
