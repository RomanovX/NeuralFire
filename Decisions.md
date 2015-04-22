# Decisions

_This file encomasses all decisions made during the meetings or as presented in the non-code issues._


## Exploration & Detection
* Pathfinding similar to ACO using levels of feromones as well as fitness of nearby droids (fitness determined through heat detection)
* Every droid can see fire, as well as everything in the adjacent grid blocks.
* Heat detection is done in range **r**
* Walls - order of complexity:
	1. At start, open room so walls only contain location
	2. In more complex rooms, walls limit detection similar to camera: you cannot see through or around walls.
	3. Consecutively, walls limit sight but allow limited heat detection *N.B. very advanced*

## Fire
* We need **n** droids near a fire to make it contained/exterminated
* Fire has temperature **T** which is related inversely to the distance from the droid.
* Currently fire is one grid block and not spreading.

## Simulation
* Code is written in Java



## Future 
* Implement fire intensities
* Time based extingishing
* Spreading of fire
* Fire has an intensity **i**, which is in direct relation to the necessary number of droids necessary for containment as well as perceived fitness through heat detection.
