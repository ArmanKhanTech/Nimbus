import datetime
import uuid
import requests
import pytz

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
    'articles': [],
    'success': True,
}

def get_news():
    for category in categories:
        if category == 'all_news':
            set_news(requests.get(
                'https://inshorts.com/api/en/news?category=all_news&max_limit=20&include_card_data=true'),
                category
            )
        elif category == 'trending':
            set_news(requests.get(
                'https://inshorts.com/api/en/news?category=trending&max_limit=20&include_card_data=true'),
                category
            )
        elif category == 'top_stories':
            set_news(requests.get(
                'https://inshorts.com/api/en/news?category=top_stories&max_limit=20&include_card_data=true'),
                category
            )
        else:
            set_news(requests.get(
                f'https://inshorts.com/api/en/search/trending_topics/{category}&max_limit=10&include_card_data=true&type=NEWS_CATEGORY'),
                category
            )

    return newsDictionary

def set_news(response, category):
    try:
        news_data = response.json()['data']['news_list']
    except Exception as _:
        news_data = None

    if not news_data:
        newsDictionary['success'] = response.json()['error']
        return newsDictionary

    for entry in news_data:
        try:
            news = entry['news_obj']
            author = news['author_name']
            title = news['title']
            imageUrl = news['image_url']
            url = news['shortened_url']
            content = news['content']
            timestamp = news['created_at'] / 1000
            dt_utc = datetime.datetime.utcfromtimestamp(timestamp)
            tz_utc = pytz.timezone('UTC')
            dt_utc = tz_utc.localize(dt_utc)
            tz_ist = pytz.timezone('Asia/Kolkata')
            dt_ist = dt_utc.astimezone(tz_ist)
            date = dt_ist.strftime('%A, %d %B, %Y')
            time = dt_ist.strftime('%I:%M %p').lower()
            readMoreUrl = news['source_url']

            newsObject = {
                'id': uuid.uuid4().hex,
                'title': title,
                'category': 'health & fitness' if category == 'Health___Fitness' else category,
                'imageUrl': imageUrl,
                'url': url,
                'content': content,
                'author': author,
                'date': date,
                'time': time,
                'readMoreUrl': readMoreUrl
            }
            newsDictionary['articles'].append(newsObject)

        except Exception:
            pass