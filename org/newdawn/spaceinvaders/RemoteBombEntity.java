package org.newdawn.spaceinvaders;

/**
 * An entity representing a explosive shot fired by the player's ship
 * 
 * @author Chris Hipkins
 */
public class RemoteBombEntity extends ShotEntity {
	/** The vertical speed at which the players shot moves */
	private double explosiveShotMoveSpeed = -150;
	/** True if this shot has been "used", i.e. its hit something */
	private boolean exploded = false;
	/** The sprite that represents the explosion when the shot is detonated */
	private Sprite explosionSprite;
	/** How long the explosion should be rendered in game */
	private long explosionTimer = 25;
	/** The time at which the shot was exploded */
	private long timeOfExplosion = 0;
	
	/**
	 * Create a new shot from the player
	 * 
	 * @param game The game in which the shot has been created
	 * @param sprite The sprite representing this shot
	 * @param x The initial x location of the shot
	 * @param y The initial y location of the shot
	 * @param angle The angle (degree) the shot is fired at
	 * @param numberOfHits The number of Entities that this shot can hit
	 * @param isFiredByPlayer An indication of whether the shot was fired by the player or an enemy
	 */
	public RemoteBombEntity(Game game,String sprite,int x,int y, double angle, int numberOfHits, boolean isFiredByPlayer) {
		super(game,sprite,x,y,angle,numberOfHits,isFiredByPlayer);

		explosionSprite = SpriteStore.get().getSprite("sprites/big_boom.gif");
		
		dy = explosiveShotMoveSpeed;
		dx = 0; // this shot should never have horizontal velovity
	}

	/**
	 * Request that this shot moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		// proceed with normal move
		super.move(delta);

		if (exploded) {
			// Since move is called every frame, check here for if the explosion has expired
			if (System.currentTimeMillis() - timeOfExplosion > explosionTimer) {
				// now that the  shot has exploded remove this from the game
				game.removeEntity(this);
				used = true;
			}
		}
	}

	/**
	 * 
	 */
	public void detonate() {
		if (!exploded) {
			exploded = true;
			sprite = explosionSprite;
			timeOfExplosion = System.currentTimeMillis();
			dy = 0; // Stop moving for the explosion
		}
	}
	
	/**
	 * Notification that this shot has collided with another
	 * entity
	 * 
	 * @param other The other entity with which we've collided
	 */
	public void collidedWith(Entity other) {
		// If the shot hasn't exploded, don't register collision
		if (!exploded) {
			return;
		}

		if (other instanceof AlienEntity && !used) {
			// notify the game that the alien has been hit
			game.notifyAlienHit(other);

			// if the number of hits has been used up, remove this shot
			if (--numberOfHits <= 0) {
				used = true;
			}
		}
	}
}