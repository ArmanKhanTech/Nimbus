import datetime
import uuid
import json
import pytz
import aiohttp
import asyncio

categories = [
    "all_news",
    "trending",
    "top_stories",
    "automobile",
    "business",
    "education",
    "entertainment",
    "fashion",
    "Health___Fitness",
    "miscellaneous",
    "politics",
    "science",
    "sports",
    "startup",
    "technology",
    "travel",
    "world"
]

newsDictionary = {
    'articles': []
}

async def fetch(session, url, category):
    timestamp = datetime.datetime.now().timestamp()
    url_with_timestamp = f"{url}&timestamp={timestamp}"
    async with session.get(url_with_timestamp) as response:
        return await response.text(), category

async def get_news():
    async with aiohttp.ClientSession() as session:
        tasks = []
        for category in categories:
            if category == 'all_news':
                url = 'https://inshorts.com/api/en/news?category=all_news&max_limit=50&include_card_data=true'
            elif category == 'trending':
                url = 'https://inshorts.com/api/en/news?category=trending&max_limit=50&include_card_data=true'
            elif category == 'top_stories':
                url = 'https://inshorts.com/api/en/news?category=top_stories&max_limit=50&include_card_data=true'
            else:
                url = f'https://inshorts.com/api/en/search/trending_topics/{category}&max_limit=50&include_card_data=true&type=NEWS_CATEGORY'
            tasks.append(fetch(session, url, category))

        responses = await asyncio.gather(*tasks)
        for response, category in responses:
            set_news(response, category)

    return newsDictionary

def set_news(response, category):
    try:
        response_data = json.loads(response)
        news_data = response_data['data']['news_list']
    except Exception as _:
        news_data = None

    for entry in news_data:
        try:
            news = entry['news_obj']
            author = news['author_name']
            title = news['title']
            image_url = news['image_url']
            source_url = news['source_url']
            content = news['content']
            timestamp = news['created_at'] / 1000
            dt_utc = datetime.datetime.fromtimestamp(timestamp, tz=pytz.UTC)
            server_tz = pytz.timezone('Asia/Kolkata')  
            dt_server = dt_utc.astimezone(server_tz)
            date = dt_server.strftime('%A, %d %B, %Y')
            time = dt_server.strftime('%I:%M %p').lower()

            newsObject = {
                'id': uuid.uuid4().hex,
                'title': title,
                'category': 'health & fitness' if category == 'Health___Fitness' else category,
                'image_url': image_url,
                'source_url': source_url,
                'content': content,
                'author': author,
                'date': date,
                'time': time,
            }
            newsDictionary['articles'].append(newsObject)
            
        except Exception:
            pass