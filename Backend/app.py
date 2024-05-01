from flask import Flask, request, jsonify
from nimbus import getNews
from flask_cors import CORS
from dotenv import load_dotenv
import os

app = Flask(__name__)
CORS(app)

load_dotenv()
api_key = os.environ.get('API_KEY')

@app.route('/')
def home():
    return jsonify({
        "message": "Nimbus API."
    }), 200

@app.route('/news')
def news():
  if request.method == 'GET':
    key = request.args.get('key')
    if not key:
      return jsonify({
        "message": "Please provide a key."
      }), 400
    else:
      if key == api_key:
        category = request.args.get("category")
        if not category:
          return jsonify({
            "error": "Please add category in query params"
          }), 404
        return jsonify(getNews(category)), 200
      else:
        return jsonify({
          "error": "Invalid API key"
        }), 401


if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0',port=5000,use_reloader=True)