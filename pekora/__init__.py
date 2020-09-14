import dotenv
import os


ROOT = os.path.realpath(os.path.join(os.path.dirname(__file__), '..'))
AUDIO_DIR = os.path.join(ROOT, 'assets', 'audio')

dotenv.load_dotenv(dotenv_path=os.path.join(ROOT, '.env'))

TOKEN = os.getenv('TOKEN')
