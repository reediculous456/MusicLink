# Requirement 1: Search for Songs

## Scenario

As a user, I should be able to search for songs through the title, artist, or album.

## Dependencies

Song data is available and accessible.

## Assumptions

Song titles are accessible with characters in the English (US) keyboard.

## Examples

### Example 1

1. **Given** a song is available
2. **When** I search for “In the End”
3. **Then** I should receive at least one result with these attributes:
   * Song: In the End
   * Album: Hybrid Theory
   * Artist: Linkin Park

### Example 2

1. **Given** a song is available
2. **When** I search for “daosidhaoigheoainfeiaf”
3. **Then** I should receive zero results (an empty list)