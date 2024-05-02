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
async def news():
  if request.method == 'GET':
    key = request.args.get('key')
    if not key:
      return jsonify({
        "message": "Please provide a API key"
      }), 400
    else:
      if key == "tomcat":
        return jsonify(get_news()), 200


if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0',port=5000,use_reloader=True)
