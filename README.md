# TripleTGame [v2.0]
Triple T, aka Kirby: The Tedhaun Treaty. A full-fledged – albeit Java-based – Kirby platformer. Originally created as a final project for my first semester of AP Computer Science (during the first week of December 2013).

**Jump to**: 

- [[Important Announcement]](#important-announcement)
- [[Download Link]](#download-link-final-release)
- [[Features & Functionality]](#features--functionality)
- [[Visuals]](#visuals)
- [[Directory Structure]](#directory-structure)
- [[TIL]](#til)
- [[Contributors]](#contributors-ing)

## Important Announcement
All of the features that I had initially planned – a full storyline, complete with animations and three worlds (/ 12 levels!) – will probably never be released. The reasons for this are twofold: **(a)** I have no users and therefore no external liability, and **(b)** I've lost interest in game design and the tedium of doing all this work myself. 

With regard to my second point, let's just say that I feel as if I've accomplished what I originally set out to do. That is, I've gotten a feel for elementary platformer logic (spritesheet animation, collision detection, movement / parallax scrolling, ...) and Swing GUI basics. From here on out, it'd mostly be fine-tuning positional coordinates, mapping out hundreds of spritesheet pixels, and animating a ton of movement sequences. Most of this would feel pretty repetitive, and again with my lack of a userbase I find myself with little motivation to continue in my game-creating endeavors. I've set my sights on bigger and more exciting projects (_what are they, you ask? Well, you'll just have to stay tuned_), and I think that it's time I gave this one some closure. Thus, I'm going to publish the game in its current unpolished form, and say that this, my friends, is the "**beta gold**" (final!) release.

Yes, it pains me a little to leave Triple T in this state, but I take a bit of solace in two of the things that I mentioned above (...i.e. literally no one cares, and I have better things to spend my time on). So yes, I'm declaring now that work on Triple T has ceased; it's time to close up shop at last.

Many thanks to all [0?] of you who supported me throughout this relatively brief development process, and I tell you now: look out for the much cooler stuff to come!

A download link for the "beta gold" version of Triple T can be found below.

## Download Link [FINAL RELEASE]
Note that by "final," I'm really trying to say "_this should've still been in development, but then I lost interest in game dev and moved on with my life._"

- [JAR file [8.1 MB]](../master/dist/v2.0/TripleT.jar?raw=true)
  - May or may not require you to change your security settings (or CTRL-click on OS X) to open.
  - Also may or may not require you to download Java.
  - By the way, if any fonts look weird on your computer, please let me know!

## Features & Functionality
Here's a quick listing of what you ~~can expect~~ might have been expecting:

- A story mode, styled as a traditional Kirby platformer (with some twists)
- Two minigames:
  - **Dodge!**: A Tedhaun evasion game
  - **kirbySMASH**: A Super Smash Bros-inspired fighter game
- Control customization
- Automatic game progress/high score retention
- Options [to change sprite colors and volume]
- Animated cutscenes that appear before major events in the storyline

## Visuals
![alt text](https://cloud.githubusercontent.com/assets/8358648/8349809/def99f52-1ad4-11e5-9b4d-1ce7ddd340d2.png "Minigame menu")
![alt text](https://github.com/ohjay/TripleTGame/blob/master/sample_imgs/sample1-1.png "The first level")
![alt text](https://github.com/ohjay/TripleTGame/blob/master/sample_imgs/sample1-2.png "The second level")
![alt text](https://github.com/ohjay/TripleTGame/blob/master/sample_imgs/sample-dodge.png "Dodge!: the minigame")

## Directory Structure
- [TripleT](https://github.com/ohjay/TripleTGame/tree/master/TripleT)

   Source code for the game, all of which resides in the TripleT package. Feel free to browse! I documented a bunch of it; I definitely wouldn't want all that work to go to waste.
- [images](https://github.com/ohjay/TripleTGame/tree/master/images)

   Visual resources used in the game. Includes menu screens, spritesheets, and miscellaneous in-game elements.

## TIL
If I had used a framework or a game engine (ex. Unity) instead of writing everything from scratch, this all would have been a lot easier and probably better quality too. Still – it's about the journey, right?

## Contributors (/ing)
**Update**: This game is no longer officially in development, but if you'd like to carry on (or try and motivate me to start working on it again), the advertisement below still stands.

```
If you'd like to join the team, you're in luck – we may just have a place for you! Either submit a pull request that's worthy of our acceptance (hint: not that hard... I think!), or send Owen an email with a description of what you could bring to the table. Or just send Owen an email, period. (I'm trying not to sound desperate here, but it's a lot of work to make a from-scratch game!)

For now, this is who we've got:

- Owen Jow [development, design]
- William Jow [graphics and aesthetics]
```
