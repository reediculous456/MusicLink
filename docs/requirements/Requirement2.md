# Requirement 2: Save Songs to Playlist

## Scenario

As a user, I should be able to compile and save songs to a playlist.

## Dependencies

Song data is available and accessible.

Song data can be stored within a container.

## Assumptions

The user has a playlist available.

## Examples

### Example 1

1. **Given** a song is available
2. **When** I search for a song and click to add to a playlist
3. **Then** I should select a playlist and receive a confirmation that the song has been added to the playlist

### Example 2

1. **Given** a song is available
2. **When** I search for a song and click to add to a playlist but don’t have any available playlists
3. **Then** I should be given a pop-up to create a playlist and receive a confirmation that the song has been added to the playlist