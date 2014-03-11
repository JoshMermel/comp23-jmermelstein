"Progress Quest" by Josh Mermelstein

[ data types and values ]
A monster is a kind of person.
A monster has a number called current hit points.
The current hit points of the monster is 10. 
The maximum score is 110.
A person has a number called kills.  The kills of the player is 0.

[ the town and shops ]
The  Town is a room.  The description of town is "The town center is mysteriously abandoned.  The three shops, the Magic Shop to the west, The General Store to the south and The Smithy to the east are all but empty.  To the north, through the Town Gate are the killing fields, full of monsters to kill for experience".

The initial appearance of the pamphlet is "You see a pamphlet on the ground. The pamphlet looks as though it were left there especially for you to read.".

The pamphlet is in The town. The description of the pamphlet is "Welcome to progress quest.  To win the game you must kill the evil king.  He can be found in the final killing fields, far North of town.".

The Iron door is a closed door. The Iron door is east of The Town and west of The Smithy.
The description of The Smithy is "This smithy smells faintly of burnt fuel and metal.  Sadly there are no useful tools left for you to 'borrow'".
There is a magic key in the Smithy.  The description of the magic key is "I bet this key unlocks a magic door!".

The Magic door is a closed, locked door.  The Magic door is unlocked by the magic key.  The Magic door is west of  Town and east of the Magic Shop. 
The description of the Magic Shop is "Adventurers use to stop here to buy potions and spells.  Unfortunately for them this was the kind of magic shop that sold cheap gags and the like.".
There is a Store key in the Magic shop.  The description of the Store key is "I bet this unlocks a store!".
There is a can of nuts in the Magic shop.  The description of the can of nuts is "Full of snakes. DO NOT OPEN!".

The General Shop door is a closed, locked door.  The General Shop door is south of town and north of the General Store.  The General shop door is unlocked by the Store key.
The description of the General store is "A place to buy and sell things.  Too bad there is no one here to trade with.".
There is a Town key in the Town.  The description of the Town key is "This unlocks the town gate".
The sword is in the General Store.  The description of the sword is "This will increase my damage".

[ fighting mechanism ]
[ Adapted from http://inform7.com/learn/man/Rex210.html#e210 ] 
Instead of attacking someone: 
	let the damage be a random number between 2 and 5; 
	if the player has the sword:
		increase  damage by 3;
	[ prevent player from hurting the evil king unless all of his minions are dead ]
	if the noun is the evil king and kills of the player < 20:
		let damage be 0;
		say "The king cannot be hurt until his minions have been slain.";
	say "You attack [the noun], causing [damage] points of damage!"; 
	decrease the current hit points of the noun by the damage; 
	if the current hit points of the noun is less than 1: 
		increase the score by 5;
		increase the kills of the player by 1;
		say "[line break][The noun]  is slain!"; 
		remove the noun from play; 
		if kills of player > 21:
			say "You have slain the evil king!  Who knows what epic fights you in the web version of progress quest: http://progressquest.com/ (not a project by me)";
			end the game in victory;

[ the killing fields and the monsters in them ]
The Town Gate is a closed, locked door. It is north of town and south of Killing Fields 1.  It is unlocked by the Town key.   
The Disembodied Hand is a monster.   The Disembodied Hand is in Killing Fields 1.  The description of the Disembodied Hand is "It looks like a weird spider but with 5 legs.".
The description of Killing Fields 1 is "The first of seven killing fields".

Killing Fields 2 is a room.  It is north of Killing Fields 1.  The description of Killing Fields 2 is "The second of seven killing fields".
The Fire worm is a monster.  It is in Killing Fields 2. The description of the Fire worm is "A worm made of fire - travelers used to capture these and cook marshmallow over them".
The Ice worm is a monster.  It is in Killing Fields 2. The description of the Ice worm is "A worm made of ice - Is it alive or just an ice sculpture?".

Killing Fields 3 is a room.  It is north of Killing Fields 2. The description of Killing Fields 3 is "The third of seven killing fields".
The shadow knight is a monster.  It is in Killing Fields 3. The description of the shadow knight is "A knight made of shadows".
The shadow horse is a monster.  It is in Killing Fields 3.  The description of the shadow horse is "The horse belonging to the shadow knight".
The shadow sword is a monster.  It is in Killing Fields 3. The description of the shadow sword is "The sword belonging to the shadow knight".

Killing Fields 4 is a room.  It is north of Killing Fields 3. The description of Killing Fields 4 is "The fourth of seven killing fields".
The cyan imp is a monster.  It is in Killing fields 4.  The description of the cyan imp is "The blood of this imp can be used as cyan printer toner in emergencies.".
The yellow imp is a monster.  It is in Killing fields 4. The description of the yellow imp is "The blood of this imp can be used as yellow printer toner in emergencies.".
The magenta imp is a monster.  It is in Killing fields 4. The description of the magenta imp is "The blood of this imp can be used as magenta printer toner in emergencies.".
The black imp is a monster.  It is in Killing fields 4. The description of the black imp is "The blood of this imp can be used as black printer toner in emergencies.".

Killing Fields 5 is a room.  It is north of Killing Fields 4. The description of Killing Fields 5 is "The fifth of seven killing fields".
The choreographer is a monster.  It is in Killing Fields 5. The description of the choreographer is "She wanted to be a garbage person when she grew up but mommy wouldn't let her".
The ballerina is a monster.  It is in Killing Fields 5. The description of the ballerina is "She wanted to be a custodian when she grew up by daddy though that was undignified.".
The break dancer is a monster.  It is in Killing Fields 5. The description of the break dancer is "She wanted to be an MMA fighter when she grew up but grandpa thought that was too dangerous".
The gymnast is a monster.  It is in Killing Fields 5. The description of the gymnast is "She wanted to be a high school teacher but graduate because she kept failing calculus.".
The supermodel is a monster.  It is in Killing Fields 5. The description of the supermodel is "She wanted to be a supermodel when she grew up.  That worked out pretty well.".

Killing Fields 6 is a room.  It is north of Killing Fields 5. The description of Killing Fields 6 is "The sixth of seven killing fields".
The kitchen ghost  is a monster.  It is in Killing Fields 6. The description of the kitchen ghost is "This ghost turns regular kitchen appliances into cursed ones".
The cursed immersion blender is a monster.  It is in Killing Fields 6. The description of the cursed immersion blender is "Used to make cursed soups".
The cursed cheese grater is a monster.  It is in Killing Fields 6. The description of the cursed cheese grater is "Used to grate cursed cheese".
The cursed popcorn popper is a monster.  It is in Killing Fields 6. The description of the cursed popcorn popper is "Used to pop cursed popcorn".
The cursed salad spinner is a monster.  It is in Killing Fields 6. The description of the cursed salad spinner is "Used to spin cursed salad".
The cursed pizza cutter is a monster.  It is in Killing Fields 6. The description of the cursed pizza cutter is "Used to cut cursed pizza".

Killing Fields 7 is a room.  It is north of Killing Fields 6. The description of Killing Fields 7 is "The last of seven killing fields".
The evil king is a monster.  The current hit points of the evil king is 100.  He is in killing fields 7.

When play begins: 
	now the left hand status line is "You have: [kills of player] kill(s)"; 
	now the right hand status line is "Your score is [score]";	


