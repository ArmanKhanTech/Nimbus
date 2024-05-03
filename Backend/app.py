from flask import Flask, request, jsonify
from nimbus import get_news
from flask_cors import CORS

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
    return jsonify(get_news()), 200


if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0',port=5000,use_reloader=True)
