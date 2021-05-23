from flask import Flask
from flask_restful import Api
from resource import TranslationResource
import os

app = Flask(__name__)
api = Api(app)

api.add_resource(TranslationResource, "/translate/<string:word>")

if __name__ == "__main__":
    app.run(debug=bool(os.getenv("DEBUG")))