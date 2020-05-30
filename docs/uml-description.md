# Class Diagram Description

## Overview

The application code is split in 3 packages - UI, DTO and DAO.

## DTO

### Playlist
The `Playlist` class is the entity class for playlists. Every playlist has a name, a list of songs added to it, and an optional owner id which corresponds the playlist with the user that created it.

### Song
The `Song` class is the entity class for songs. Each song has a title, album and artist.

## DAO

### IPlaylistDAO
This interface defines the methods to get the playlist information.

### ISongDAO
This interface defines the methods to get the information for a song.

### AppDatabase
The AppDatabase class acts as a proxy for communicating with the RoomDB database, and consists of methods to save and fetch playlist data from the database.

## UI

### PlaylistsActivity
This activity is for the main screen where the user can view their playlists and create new playlist. This activity uses the `PlaylistsViewModel` class for data.

### PlaylistsViewModel
This viewmodel uses the Playlist DTO to display all the playlists created and creates new playlists.

### PlaylistActivity
This activity is for the playlist screen that shows the songs in the playlist, shows a button to add more songs, and a floating button to share the playlist.

### PlaylistViewModel
This viewmodel uses the Playlist DTO and Song DTO to display the individual playlist details and the list of songs.

### SongSearchActivity
This activity is for the screen that lets the user search for songs to add into their playlists. Uses the `SongSearchViewModel` class.

### SongSearchViewModel
This viewmodel uses the `RetrofitClientInstance` class to search songs from different APIs and show the results.

### RetrofitClientInstance
The RetrofitClientInstance class communicates with REST APIs to gather song data.

### SongActivity
This activity is used for a popup that displays song details when clicked on a search result and provides option to add to playlist.

### SongViewModel
This viewmodel is used by the SongActivity class to display the song details in the popup.
