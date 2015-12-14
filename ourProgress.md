## 17.11.2011 ##

The last two weeks we spent for upgrading our performance measurement tool. This now enables multiple runs in a row, what makes evaluation much easier. We also implemented two player types using statistic methods and heuristics. Also started to write the final paper.

In the last two weeks, we will mainly do some experiments with our different players so that we can evaluate them and come to an conclusion which approaches perform good which don't.

## 02.11.2011 ##

The last week we tried to find a way to get a set of all possible models from the knowledge base. For example in form of a query "I have 2 remaining ships with a length of 3. What are all possible combinations to place these two ships w.r.t. the current infomration in the knowledge base?" Unfortunately, wwe didn't a way to realize this until know. We tried different ideas but prolog didn't answer with helpful solutions.

Nevertheless, at least we found a way to get all possible placings for exactly one ship. E.g. a query "Where can a ship with a length of 2 be placed?" is answered correctly w.r.t. the current information.

Assume the following field

?   ?   X

?   X   ?

X   X   ?

A query that asks for all possibilities to place a ship with a length of two would return the result set "{00, 01}, {00, 10}, {21, 22}".

For the field

?   X   X

X   ?   X

X   X   ?

the result of the same query would be empty.

What we now want to do is to use this approach to compute statistical data. We try to find out the probabilities of including a ship for all fields. Therefore, different approaches are imaginable:
  * For every ship type (different lengths) get the possible positions and than iterate over all solutions for every ship type and search for the field with the maximal occurence.
  * Do not iterate over the whole result set but do it seperately for every ship type. For example you have one remaining ship with a length of 5 and there are 2 possibilities where a ship of this length can be placed. Then, for every field of the result set, you have a probability of 50% (whats quite high) that it includes a ship. This isn't considered in the first approach.
  * A combination of the first two approaches.
  * Do a brut force approach. Create a solution that includes all possible combinations of ships (w.r.t. to the remaining number). This would be the most powerful approach, but the time for computation might be a problem.

Perhaps, we create different solutions and then will compare them among each other.

## 26.10.2011 ##

We achieved an implementation based on Prolog that is equivalent to our Medium Player approach. This player sets up a knowledge base in the beginning were it defines basic knowledge about the fields, their common edges, etc. It is also possible to insert information about the games progress. If a field was found to be empty this information is also added to the knowledge base. In addition, it includes knowledge about found ships, fields belonging to these ships and so on. On the foundation of a few more or less simple rules it is possible to infer alternatives for the next move. If the player found a ship but didn't sink it yet, it is able to find the fields that can include other parts of this ship.

We also let compete this player against our Medium Player - they achieve the same performance.

Our next steps are:
Identifying weaknesses.
  * The remaining ships are not taken into account so far.
  * The player always attacks a random field when there isn't any hit but not sunk ship.

We try to find ways that enable the player to make assumptions about probabilities of different moves (is there a move with a higher probability f success than other moves?). We aren't sure how much of this can be done by the use of Prolog, or if we have to add additional features/libraries.

## 20.10.2011 ##

After playin' around a bit with different Prolog libraries we found a library that seems to be helpful for our purposes. It is the tuProlog API, that provides a simple and straightforward approach to infer new knowledge from the knowledge base.

Currently, we are working on the structure for a knowledge base that can support the AI player.

## 12.10.2011 ##

The last two weeks we spended for a clean setup of a test environment. This is now running stable so far. It includes the following parts: a settings section where the user can specify the options (number of ships and type of player). A game section where the game can be started and the progress of the player is visualized. And a statistics section that isn't implemented yet but hopefully will be in 1 - 2 days.

There are two different types of players - a random player (that doesn't perform very well and often needs 100 tries to hit all ships on the 10 x 10 field ;) ) and a Medium-skilled player that is based on a quite simple structure but doesn't perform too bad.

Now, our target is to create a third type of player (that easily can be plugged in into the existing system) that is based on AI techniques such as Logic and decision making under uncertainty. We scheduled a meeting for tomorrow (13.10.) so that we can discuss out future work and start to elaborate the AI approach.

In the Downloads-Area you also can find a stable running executable jar file of the current state of our system. Please complain if it doesn't run on your system properly - you should see Icons on the tabs and the buttons of the field in the Play-area have to be coloured by icons, too.