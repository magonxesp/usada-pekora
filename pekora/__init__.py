import dotenv
import os


ROOT = os.path.realpath(os.path.join(os.path.dirname(__file__), '..'))
PACKAGE_DIR = os.path.realpath(os.path.dirname(__file__))
AUDIO_DIR = os.path.join(ROOT, 'assets', 'audio')

dotenv.load_dotenv(dotenv_path=os.path.join(ROOT, '.env'))

TOKEN = os.getenv('TOKEN')
ENV = os.getenv('BOT_ENV')
GOOGLE_API_KEY = os.getenv('GOOGLE_API_KEY')

if ENV == "dev" or ENV == "development":
    import pydevd_pycharm

    pydevd_pycharm.settrace(
        'host.docker.internal',
        port=5000,
        stdoutToServer=True,
        stderrToServer=True
    )
