from flask_restful import Resource
from scrapper import Translator

class TranslationResource(Resource):

    def __init__(self):
        self.translator = Translator()

    def get(self, word):
        result = self.translator.get_translation(word)
        self.translator.teardown()
        return result

