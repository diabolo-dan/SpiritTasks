
Coding Challenges for Spirit AI, uploaded to github as requested.


# Issues/TODO:
## RomanMaths/roman-frontend:
 - Logging is entirely missing. At a minimum logging start-up, and each request/response would be good.
 - Error handling is not ideal.  The error codes returned aren't the most accurate.  I was disappointed by casks set up for this.  Solution would likely require switching out cask, or implementing an extra layer for errors, rather than using the HTTP layer.
 -- I think I've worked out cask a bit more, so I could get to a better solution with error handling with a bit more time.
 - Dockerfile re compiles all of scala each time a change occurrs, skipping sbts caching. This could be fixed by compiling locally, but that sidesteps some of the dependency advantages of docker.
 - Prettify the webpage.

## pink_flamingo
 - Python3 dependency.  Might be sensible to dockerise.
 - Isn't obviously extensible (in terms of functionality).  Could I guess be combined in some way with the RomanMaths server to provide RomanFizzBuzz.
