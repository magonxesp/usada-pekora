from typing import Optional
import discord
import os
import pekora
import yaml
import re


class MessageResponse:

    _message: discord.Message
    _response_text: str
    _response_audio: str

    def __init__(self, message: discord.Message):
        self._message = message
        self._response_text = ""
        self._response_audio = ""

    async def _play_audio(self, author: discord.Member, audio_file_name: str):
        if author.voice:
            channel: discord.VoiceChannel = author.voice.channel
            client: discord.VoiceClient = await channel.connect()

            client.play(discord.FFmpegPCMAudio(os.path.join(pekora.AUDIO_DIR, audio_file_name)))

            while client.is_playing():
                pass

            await client.disconnect()
    
    def _get_responses(self) -> Optional[list]:
        with open(os.path.join(pekora.PACKAGE_DIR, 'pekora.yml'), 'r') as file:
            responses = yaml.load(file)

            if 'pekora' in responses.keys() and 'responses' in responses['pekora'].keys():
                return responses['pekora']['responses']
            else:
                return None

    def _compare(self, _input: str, message_text: str, comparator: str) -> bool:
        if comparator == "in":
            return _input.lower() in message_text.lower()
        elif comparator == "pattern":
            return re.match(_input, message_text) is not None

    async def response_text(self):
        if self._response_text != "":
            channel: discord.abc.Messageable = self._message.channel
            await channel.send(self._response_text)

    async def response_audio(self):
        if self._response_audio:
            await self._play_audio(self._message.author, self._response_audio)

    def process_message(self):
        responses = self._get_responses()

        if responses:
            for response in responses:
                if self._compare(response['input'], self._message.content, response['compare']):
                    self._response_text = response['text']
                    self._response_audio = response['audio']
                    break
