# CatGame the cat game

## Overview

CatGame the cat game is a rogue like sidescroller.

## Recent updates/additions

I took a few days to design an easy way to create, load and update levels.
You add a new level to the MakeLevels class and you load them from the GameView.
Each level updates and maintains all actions even bounds checking.

## Still to do

- A lot
- Levels need to control carefully how the ground is placed, I may need to overide that method or create an external method for that. 

What I mean is if you have 3 heights of ground and the 3rd cannot be reached without the 2nd then you would need to make sure that the randomly chosen ground at least follows that rules. I may get away by doing some sort of importance rating. I will figure this out next.

- Implement evildoers
- Implement life and pain
- Screen for editing your character and choosing when to play
- Screen for modifying your character base traits
- Screen for choosing a different character
- Create more levels of increasing difficulty
- Make the art for all this
- Make music for the background
- Get Andrew to do the recording for the cats meow.
- Fix buttons and make the cat easier to control (or leave it as is im lazy)
