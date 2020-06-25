**Movie Streaming Information Database (MSIDB)**
================

## **1. Objectives:**

### **General Purpose:**

- Provides the user with:
  - An easy to use All-in-One movie database
  - Up to date streaming service information

- Gives the user the ability to find relevent movie information and the places they can be watched as well.

### **Detailed Functionalities:**

- Finds movies from TheMovieDB that match the user search phrases and display the results.

- For each search result: requests TheMovieDB (and other websites if necessary) for general information (rating, description, images, etc.), and check from Netflix, Hulu, Amazon for available streaming services.

- Slide shows for trending movies or recent search results.

- Filter movies by streaming services.

- Check if a movie is available from the user's local cinemas (if yes, provides the users with detailed time and location)

## 2. Implementation:

- The application will be compatible with Android devices running Android 6.0 or newer, which is more than 60% of the Android devices globally and all those released since 2016.

- The following APIs will be utilized to fetch information from the above websites:
  - TheMovieDB
  - Utelly (from RapidAPI)

- On the home page, trending movies and recently searched movies will be shown. They will be grouped by streaming services and organized in scrollable rows.

- There will be a navigation tab on the bottom of the app to filter all movies by streaming services. From there, the user can continue filtering the movies by genres, age ratings, IMDB ratings, etc.

- The search functionality will utilize Instant Searching (similar to Google's search engine)
  - An Internet connection speedtest will be performed to decide whether Instant Search should be enabled. If not, only search suggestions will be shown, instead of the instant results.
  - The user's search phrases will be fetched as they type.
  - For each search phrase recorded, search from the movie database for the results by name and fetch the movie infos with included phrases.
  - Sort the movies by relevance, then return the results for the typed search phrases.
  - For each result, fetch the full result's information for 15 movies. As the user scrolls, information for extra results will be fetched. This is to prevent redundant data requests that would never be shown because the user would never scroll to them.
  - As the user's search phrases change, the results will be updated in real time (the whole above process will be repeated). Also, their search phrases will be auto-filled if they match a movie's name.
  - The search results will be filterable.

- Up to 1000 search results per user search query will be cached in the form or SQL database.
  - The number of API requests per search will be minimized. This helps reduce the app maintenance cost and make search queries less dependant to the Internet connection speed. As a result, future searches will potentially be faster.
  - The caching process will be done in background with limited bandwidth in order to minimize interferences.
  - The cache's size will be no more than 300 MiB to save internal storage. Search results older than 6 months old will also be deleted.

- When the user clicks on a movie, either from the app's homepage or search results, another page will be shown, showing that movie's details.
  - The movie's detailed information will first be fetched from the mentioned above SQL database. Then, the missing information will be fetched using API requests.
  - When the user clicks on an actor/ actress, a website will be opened showing their details.

## **3. Layout (mock-up):**

[Click here for the Screenshots of the Application's Mock-up Layout](https://github.com/UPEI-Android-2018/group-projects-2019-nebula/tree/master/Mock-up_Layout_Screenshots)

## **4. General Timeline:**

### **Phase #1:** Basic Search Engine Implementation

**Estimated Completion Date:** 31 Oct 2019

- API requesting classes implemented, ensuring that search functionalities are always available.
- GUI fully designed with at least elements for search queries being functional.
- Basic search engine (without instant searching) implemented.

### **Phase #2:** Movie Filtering Implementation

**Estimated Completion Date:** 14 Nov 2019

- Filtering engine completed.
- GUI elements for filtering being functional.
- Slide shows for movies in the app's home screen.
- Classes for SQL Databases.

### **Phase #3:** Instant Search Engine Implementation

**Estimated Completion Date:** 25 Nov 2019

- Instant searches being functional.
- GUI elements being fully functional.
- Classes for SQL queries completed in preparation for Phase #4.
    
### **Phase #4:** SQL Database Implementation

**Estimated Completion Date:** 05 Dec 2019

- Hybrid search engine of SQL-querying and API-querying implemented.
- Caching functionalities implemented.
- Background tasks fully implemented.
- Visual improvements for UI design.
  
### Phase #5: Optimizations and Completion

**Estimated Completion Date:** 14 Dec 2019
  
- Further app optimizations.
- Implemented the functionalities to show nearby movie theaters available for the selected movies.
- Project Wiki built with all necessary documentations available.

## **5. Task Allocation:**

### **▶ Dvir Katz:**
- API & Database Manager:
  - MovieDB.org API Implementer
  - Assistance with Utelly API Implementation
  - Assistance with Map of Movie Theaters Implementation
- Proposal Contributor:
  - General Purpose Writer
  - Mock-up Layout Designer
  - Assisting Proposal Completion

### **▶ Hasham Hamshary:**
- Graphical User Interface Designer:
  - On-Click Methods Implementer
  - Slideshow Functionalities
- Proposal Contributor:
  - Mock-up Layout Designer
  
### **▶ Nghia Tran:**
- Backend Implementer:
  - Search Engine Implementer
  - Background Tasks Implementer
  - Search Results Caching
  - Movie Filters Implementer
  - Application Optimization
- Proposal Writer:
  - General Editor
  - Detailed Functionalities Writer
  - Implementation Writer
  - General (Estimated) Timeline Writer
  - Markdown Formatting

### **▶ Remah Badr:**
- API & Database Manager:
  - Maps of Movie Theaters
  - Utelly API Implementation
