import datetime
import uuid
import requests
import pytz

def getNews(category):
    if category == 'all_news':
        response = requests.get(
            'https://inshorts.com/api/en/news?category=all_news&max_limit=25&include_card_data=true')
    elif category == 'trending':
        response = requests.get(
            'https://inshorts.com/api/en/news?category=trending&max_limit=25&include_card_data=true')
    elif category == 'top_stories':
        response = requests.get(
            'https://inshorts.com/api/en/news?category=top_stories&max_limit=25&include_card_data=true')
    else:
        response = requests.get(
            f'https://inshorts.com/api/en/search/trending_topics/{category}&max_limit=25&include_card_data=true')
    try:
        news_data = response.json()['data']['news_list']
    except Exception as _:
        print(response.text)
        news_data = None

    newsDictionary = {
        'success': True,
        'category': category,
        'data': []
    }

    if not news_data:
        newsDictionary['success'] = response.json()['error']
        newsDictionary['error'] = 'Invalid category'
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
                'imageUrl': imageUrl,
                'url': url,
                'content': content,
                'author': author,
                'date': date,
                'time': time,
                'readMoreUrl': readMoreUrl
            }
            newsDictionary['data'].append(newsObject)
        except Exception:
            print(entry)
    return newsDictionary