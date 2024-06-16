from flask import Flask, request, jsonify
from flask_cors import CORS

import asyncio
import os

from news import get_news
from weather import get_weather

app = Flask(__name__)
CORS(app)

key = os.getenv('API_KEY')

@app.route('/')
def home():
    return jsonify({
        "message": "Welcome to Nimbus."
    }), 200

@app.route('/news')
def news():
    if request.method == 'GET':
        if 'key' in request.args:
            if request.args['key'] == key:
                loop = asyncio.new_event_loop()
                asyncio.set_event_loop(loop)
                result = loop.run_until_complete(get_news())
                return jsonify(result), 200
            else:
                return jsonify({
                    "message": "Invalid API key."
                }), 403
        else:
            return jsonify({
                "message": "API key is required."
            }), 400
        
@app.route('/weather')
def weather():
    if request.method == 'GET':
        if 'key' in request.args:
            if request.args['key'] == key and request.args['city'] != "Unknown":
                loop = asyncio.new_event_loop()
                asyncio.set_event_loop(loop)
                result = loop.run_until_complete(get_weather(request.args['city']))
                return jsonify(result), 200
            else:
                return jsonify({
                    "message": "Invalid API key or Location."
                }), 403
        else:
            return jsonify({
                "message": "API key is required."
            }), 400


if __name__ == '__main__':
    app.debug = True
    app.run(
        host = '0.0.0.0', 
        port = 5000, 
        use_reloader = True
    )
