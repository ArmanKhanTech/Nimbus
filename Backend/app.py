from flask import Flask, request, jsonify
from nimbus import get_news
from flask_cors import CORS
import asyncio

app = Flask(__name__)
CORS(app)

@app.route('/')
def home():
    return jsonify({
        "message": "Nimbus API."
    }), 200

@app.route('/news')
def news():
    if request.method == 'GET':
        loop = asyncio.new_event_loop()
        asyncio.set_event_loop(loop)
        result = loop.run_until_complete(get_news())
        return jsonify(result), 200


if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0',port=5000,use_reloader=True)
