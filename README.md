This Was my Android class project, I handeld API connection and adapters, which transferred the movie information to be used by the UI

Code was written by me and 2 others listed below

### 1. Features & More:

* Movie Search
* Streaming Availability
* Movie Details
* Actors Details
* Trailers
* Reviews
* Movie Adapter
* Actor Adapter
* Click Listeners
* Fragments
* RecyclerView for displaying posters
* ASyncTask for contacting the API
* Utelly Movie API
* ArrayAdapter for storing movies
* ViewHolders for inflating the row layout from xml when needed

### 2. Implementation:
The application will be compatible with Android devices running Android 6.0 or newer, which is more than 60% of the Android devices globally and all those released since 2016.

The following APIs will be utilized to fetch information from the above websites:

TheMovieDB
Utelly (from RapidAPI)
On the home page, trending movies and recently searched movies are shown, do note that Utelly, unlike Guidebox (our trial API) does not provide top movies therefore, only placeholder movies are shown. They are grouped by streaming services and organized in scrollable rows.


### 3. API functionality

The App is using 2 API the moviedb: https://www.themoviedb.org/documentation/api, Utelly: https://rapidapi.com/utelly/api/utelly and previously, Guidebox: https://www.guidebox.com/

The App has 3 API related classes, MovieDriver, MovieAPI and MovieInterface. 

MovieDriver: handles instructions and passes information to MovieAPI using an interface

MovieAPI: handles requests to the APIs and passes the results to MovieInterface

MovieInterface: holds lists of subscribing Activities, when an update from MovieAPI arrives all relevnt activities are updated with the information

### 4. Nebula Home Page

There are three streaming services currently available: Netflix, Amazon Prime, and Google Plus. This page displays movies of those streaming services in recycler views. This page also has a search button which takes you to the search page.

### 5. Movie Page

This page displays movie details and three fragments for trailer, actors, and reviews. 

### 6. Screenshots:

<img width="325" alt="Screen Shot 2019-12-15 at 11 59 33 AM" src="https://user-images.githubusercontent.com/55146413/70865321-9fea9c00-1f32-11ea-80e1-57d6d09f95de.png">

<img width="323" alt="Screen Shot 2019-12-15 at 11 59 59 AM" src="https://user-images.githubusercontent.com/55146413/70865322-9fea9c00-1f32-11ea-8a72-d3d07919fb00.png">

<img width="326" alt="Screen Shot 2019-12-15 at 10 59 08 AM" src="https://user-images.githubusercontent.com/55146413/70864615-23ec5600-1f2a-11ea-8ebd-f470286af9a4.png">

<img width="325" alt="Screen Shot 2019-12-15 at 10 31 54 AM" src="https://user-images.githubusercontent.com/55146413/70865139-644ed280-1f30-11ea-9aa3-01ccf18e6422.png">

<img width="323" alt="Screen Shot 2019-12-15 at 11 39 29 AM" src="https://user-images.githubusercontent.com/55146413/70865105-facec400-1f2f-11ea-9929-36718c7de78a.png">

<img width="326" alt="Screen Shot 2019-12-15 at 11 40 10 AM" src="https://user-images.githubusercontent.com/55146413/70865106-fd311e00-1f2f-11ea-8b88-ecca2bf5b678.png">

<img width="326" alt="Screen Shot 2019-12-15 at 11 40 32 AM" src="https://user-images.githubusercontent.com/55146413/70865109-ff937800-1f2f-11ea-99fe-ccdfd20d77a4.png">

<img width="324" alt="Screen Shot 2019-12-15 at 11 40 48 AM" src="https://user-images.githubusercontent.com/55146413/70865110-01f5d200-1f30-11ea-973e-67a89aaa3515.png">

### 7. Requirements:
* Internet Connection
* Android Version 28 or Higher

### 8. Author:
Hesham

Remah Badr rbadr@upei.ca

Dvir Katz Dvir.katz.pro@gmail.com

