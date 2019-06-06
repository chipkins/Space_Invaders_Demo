package org.newdawn.spaceinvaders;

/**
 * An entity representing a shot fired by the player's ship
 * 
 * @author Kevin Glass
 */
public class ShotEntity extends Entity {
	/** The game in which this entity exists */
	protected Game game;
	/** True if this shot has been "used", i.e. its hit something */
	protected boolean used = false;
	/** The number of Entities that this shot can hit before it becomes 'used' */
	protected int numberOfHits = 0;
	
	/** The vertical speed at which the players shot moves */
	private double playerMoveSpeed = -300;
	/** The vertical speed at which the players shot moves */
	private double alienMoveSpeed = 100;
	/** Value denoting whether the bullet is from the Player or an Alien */
	private final boolean isFiredByPlayer;
	
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
	public ShotEntity(Game game,String sprite,int x,int y, double angle, int numberOfHits, boolean isFiredByPlayer) {
		super(sprite,x,y);
		
		this.game = game;

		double angleRadians = Math.toRadians(angle);
		
		if (isFiredByPlayer) {
			// Because we're treating player's forward vector as '0 degrees' we are switching sin and cos for dx and dy respectively
			dx = playerMoveSpeed * Math.sin(angleRadians);
			dy = playerMoveSpeed * Math.cos(angleRadians);
		} else {
			dx = alienMoveSpeed * Math.sin(angleRadians);
			dy = alienMoveSpeed * Math.cos(angleRadians);
		}

		this.numberOfHits = numberOfHits;
		this.isFiredByPlayer = isFiredByPlayer;
	}

	/**
	 * Request that this shot moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		// proceed with normal move
		super.move(delta);
		
		// if we shot off the screen, remove ourselfs
		if (y < -100) {
			game.removeEntity(this);
		}
	}
	
	/**
	 * Notification that this shot has collided with another
	 * entity
	 * 
	 * @param other The other entity with which we've collided
	 */
	public void collidedWith(Entity other) {
		// prevents double kills, if we've already hit something,
		// don't collide
		if (used) {
			return;
		}
		
		if(isFiredByPlayer)	{
			// if we've hit an alien, kill it!
			if (other instanceof AlienEntity) {			
				// notify the game that the alien has been hit
				game.notifyAlienHit(other);

				// if the number of hits has been used up, remove this shot
				if (--numberOfHits <= 0) {
					game.removeEntity(this);
		
					used = true;
				}
			}
		} else {
			if (other instanceof ShipEntity) {
				game.notifyPlayerDamaged();

				// if the number of hits has been used up, remove this shot
				if (--numberOfHits <= 0) {
					game.removeEntity(this);
		
					used = true;
				}
			}
		}
	}
}