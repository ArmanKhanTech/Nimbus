[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<br />
<div align="center">
  <a href="https://github.com/ArmanKhanTech/Nimbus/">
    <img src="https://github.com/ArmanKhanTech/Nimbus/assets/92728787/b5c7bc38-e934-41aa-96fd-8a0796dac8e6" alt="Logo" width="100" height="90" >
  </a>

  <h3 align="center">Nimbus</h3>
  <p align="center">Status: Completed</p>
  <p align="center">A summarized news & weather app.</p>

  <p align="center">
    <a href="https://github.com/ArmanKhanTech/Nimbus"><strong>Explore the docs »</strong></a>
    <br />
    <a href="https://github.com/ArmanKhanTech/Nimbus/issues">Report a Bug</a>
    ·
    <a href="https://github.com/ArmanKhanTech/Nimbus/issues">Request new Feature</a>
  </p>
</div>
<br />



<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About the Project</a></li>
    <li><a href="#getting-started">Getting Started</a></li>
    <li><a href="#screenshots">Screenshots</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



## About the Project

Welcome to Nimbus, designed to keep you effortlessly informed and updated. Our app offers succinct summaries of the day's top stories, ensuring you stay informed without overwhelming you with lengthy articles. You can dive deeper into specific topics with categorized news sections such as Top Stories, Trending topics, and more, providing comprehensive coverage across various interests. Bookmark articles that catch your eye for later reading and personalize your news feed with our easy-to-use search function that allows you to find articles by keywords. Stay ahead of the weather with real-time updates, offering detailed forecasts on both daily and hourly bases. Whether you prefer to browse in Light or Dark Mode, our app ensures a customizable viewing experience to suit your preference. Sign up today to unlock these features and start enjoying a streamlined news and weather experience tailored just for you.

**Supports Android 10 & above only.**


### Features

<ul>
  <li><strong>Summarized News:</strong> Concise summaries of current events and headlines.</li>
  <li><strong>News Categories:</strong>
    <ul>
      <li><strong>Top Stories:</strong> Highlighted news articles and breaking stories.</li>
      <li><strong>All News:</strong> Comprehensive coverage across all categories.</li>
      <li><strong>Trending:</strong> Popular topics gaining traction in real-time.</li>
      <li><strong>Recent:</strong> Recently published articles and updates.</li>
      <li><strong>Bookmark:</strong> Save articles to read later.</li>
    </ul>
  </li>
  <li><strong>Articles by Topics:</strong> Explore articles categorized into various topics of interest.</li>
  <li><strong>Search Articles by Keyword:</strong> Find specific articles using keywords or phrases.</li>
  <li><strong>Bookmark Articles:</strong> Save favorite articles for easy access.</li>
  <li><strong>Real-time Weather Updates:</strong>
    <ul>
      <li><strong>Daily & Hourly:</strong> Up-to-date weather forecasts, including daily and hourly updates.</li>
    </ul>
  </li>
  <li><strong>Signup & Login:</strong> Register and log into your account to personalize your news experience.</li>
  <li><strong>Light & Dark Mode:</strong> Customize the interface with light or dark themes for optimal viewing.</li>
</ul>


### Built with

<li><img src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" /></li>
<li><img src="https://img.shields.io/static/v1?style=for-the-badge&message=Jetpack+Compose&color=4285F4&logo=Jetpack+Compose&logoColor=FFFFFF&label=" /></li>
<li><img src='https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white' /></li>
<li><img src="https://img.shields.io/badge/flask-%23000.svg?style=for-the-badge&logo=flask&logoColor=white" /></li>
<li><img src="https://img.shields.io/badge/firebase-%23039BE5.svg?style=for-the-badge&logo=firebase" /></li>
<br />


## Getting Started

Follow the below instructions to get started.


### Prerequisites

<ol>
  <li>
    <p>Android Studio</p>
  </li>
  <li>
    <p>Kotlin 1.9+</p>
  </li>
  <li>
    <p>Python 3.12</p>
  </li>
</ol>



### Installation

1. Clone this repository:
   
   ```sh
   git clone https://github.com/ArmanKhanTech/Nimbus.git
   ```

2. Install all the backend dependancies:
    
   ```sh
   cd Backend
   pip install -r requirements.txt
   ```

3. Create `.env` file:

   ```sh
   touch .env
   ```

4. Add `API_KEY=[YOUR_API_KEY]` to `.env`.
   
5. Run the Flask development server:
   
   ```sh
   py app.py
   ```
   
6. Navigate to browser & verify functioning of both the endpoints:<br />
   a. `/news?key=[YOUR_API_KEY]`<br />
   b. `/weather?key=[YOUR_API_KEY]&city=[CITY_NAME]`<br />

7. Also, add `API_KEY=[YOUR_API_KEY]` to `local.properties` in `Frontend/local.properties`.

8. Run the app.


## Screenshots
<img src="https://github.com/ArmanKhanTech/Nimbus/assets/92728787/c118e706-0a04-4f21-b5fb-dda6acabf699" alt="Screenshot" width="250" height="500">
<img src="https://github.com/ArmanKhanTech/Nimbus/assets/92728787/19e7e646-f4e6-4cb1-b632-314ec3c766fd" alt="Screenshot" width="250" height="500">
<img src="https://github.com/ArmanKhanTech/Nimbus/assets/92728787/4f1cfe53-f6fe-4057-9ac3-288ad3c888e0" alt="Screenshot" width="250" height="500">
<img src="https://github.com/ArmanKhanTech/Nimbus/assets/92728787/c9fc8e7a-7720-47d4-ae74-d5206f54376f" alt="Screenshot" width="250" height="500">
<img src="https://github.com/ArmanKhanTech/Nimbus/assets/92728787/9da179f4-4354-40ff-9b0b-73d575efd0fa" alt="Screenshot" width="250" height="500">
<img src="https://github.com/ArmanKhanTech/Nimbus/assets/92728787/b78a942a-14e3-4f20-a385-cd3c50c90d55" alt="Screenshot" width="250" height="500">
<img src="https://github.com/ArmanKhanTech/Nimbus/assets/92728787/75a21c66-0134-48d8-9b9f-977e8aa6a95b" alt="Screenshot" width="250" height="500">
<img src="https://github.com/ArmanKhanTech/Nimbus/assets/92728787/988dd9f2-b04f-49a9-8f8c-61dc70111aaf" alt="Screenshot" width="250" height="500">
<img src="https://github.com/ArmanKhanTech/Nimbus/assets/92728787/90e8621b-7194-4ecf-84b3-77da10e28b2c" alt="Screenshot" width="250" height="500">



## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".

Don't forget to give the project a star! 

Thanks again!



## License

Distributed under the MIT License. See `LICENSE` for more information.



## Contact

Arman Khan - ak2341776@gmail.com

Project Link - [https://github.com/ArmanKhanTech/Nimbus](https://github.com/ArmanKhanTech/Nimbus)



[contributors-shield]: https://img.shields.io/github/contributors/ArmanKhanTech/Nimbus.svg?style=for-the-badge
[contributors-url]: https://github.com/ArmanKhanTech/Nimbus/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/ArmanKhanTech/Nimbus.svg?style=for-the-badge
[forks-url]: https://github.com/ArmanKhanTech/Nimbus/network/members
[stars-shield]: https://img.shields.io/github/stars/ArmanKhanTech/Nimbus.svg?style=for-the-badge
[stars-url]: https://github.com/ArmanKhanTech/Nimbus/stargazers
[issues-shield]: https://img.shields.io/github/issues/ArmanKhanTech/Nimbus.svg?style=for-the-badge
[issues-url]: https://github.com/ArmanKhanTech/Nimbus/issues
[license-shield]: https://img.shields.io/github/license/ArmanKhanTech/Nimbus.svg?style=for-the-badge
[license-url]: https://github.com/ArmanKhanTech/Nimbus/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/arman-khan-25b624205/
