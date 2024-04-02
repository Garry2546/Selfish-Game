Implementation of a card game, Selfish (Space Edition).
## Game Specs
This is a game for 2-5 players. There is one difference in the rules for a two-player game that
you should be aware of.

## Death
A player dies anytime they run out of Oxygen cards. This may be during their turn, or it may
be during someone else's turn. The ONLY exception is  you are allowed to discard your final 2 Oxygens to travel
forward in space, in the hope that your Space Card grants you additional oxygen. However, if it doesn't, you die and are out of the game.

Note that you die and are out of the game even if travelling forward in space has put you
level with the ship.
Dead players should immediately discard their hand. In the original game, the player card for
a dead player remains at the top of the stack but is flipped over. In this coursework, the track
for a player therefore remains unchanged when the player dies.

## Game (Action)Cards
Although it might be foolish to do so, there's no rule that says you can't target a dead player
(it's up to you whether your game allows this).
# Hole in Suit: The targeted Oxygen cards go into the Game Card Discard pile.
# Oxygen Siphon: The targeted Oxygen cards go into the current player's hand.
# Rocket Booster: The player follows all the usual steps for travel (i.e. they draw a Space Card) but without any need to discard Oxygen.

## Space Cards
# Gravitational Anomaly: At no point does this card enter the drawing player's track.
# Hyperspace: This card is placed in the track and a second Space Card is immediately drawn.
# Solar Flare: When this card is at the top of a players track (i.e. directly behind them) thenthat player cannot play any Game Cards during their turn, nor can they play a Sheild card to block another player's action. However, all other aspects of the game continue as normal for the player (card pickup, breathe/move/discard).
# Tether: The card from the track of the player being moved back goes to the space discard pile. The player moving forward draws from the space deck.
# Wormhole: When two players swap places, it is only their track of Space Cards that are swapped. All other player/Astronaut state is unchanged. Thus, if a player trades places with a corpse, the dead player remains dead, and the alive player remains alive. If a player swaps with a player who is directly in front of a Solar Flare, then the swapping player is now subject to the effects of the Solar Flare and the swapee is no longer subject to those effects.

## Running the code

This excercise you can implement a console or JavaFX application as you prefer.
We have provided a (near-empty) console application in your repository, we refer to this in other documents as the "driver". To run the provided driver:

```
> cd src/main
> javac GameDriver.java
> java GameDriver
```


