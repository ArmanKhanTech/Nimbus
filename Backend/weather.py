import python_weather

async def get_weather(city):
    weatherDictionary = {
        'day': []
    }

    async with python_weather.Client(unit = python_weather.METRIC) as client:
        weather = await client.get(city)

    for daily in weather.daily_forecasts:
        weatherDictionary['day'].append({
            'date': daily.date,
            'temperature': daily.temperature,
            'hourly': []
        })

        for hourly in daily.hourly_forecasts:
            weatherDictionary['day'][-1]['hourly'].append({
                'time': hourly.time.strftime('%I:%M %p').lower(),
                'temperature': hourly.temperature,
                'description': hourly.description,
                'kind': hourly.kind.name if hourly.kind else None
            })

    return weatherDictionary