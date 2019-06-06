package org.newdawn.spaceinvaders;

import java.awt.event.KeyEvent;

/**
 * The entity that represents the players ship
 * 
 * @author Kevin Glass
 */
public class ShipEntity extends Entity {
	/** The game in which the ship exists */
	private Game game;
	/** The sprite that represents the explosion when the ship is detonated */
	private Sprite explosionSprite;
	/** The Player's current health value */
	private int health = 3;

	/** The ships weapon system, used to manage shot pattens and weapon types */
	public WeaponSystem weapons;
	
	/**
	 * Create a new entity to represent the players ship
	 *  
	 * @param game The game in which the ship is being created
	 * @param ref The reference to the sprite to show for the ship
	 * @param x The initial x location of the player's ship
	 * @param y The initial y location of the player's ship
	 */
	public ShipEntity(Game game,String ref,int x,int y) {
		super(ref,x,y);
		
		this.game = game;

		weapons = new WeaponSystem(game, this);

		explosionSprite = SpriteStore.get().getSprite("sprites/boom.gif");
	}

	/**
	 * Get the current health value for the player
	 * 
	 * @return The Player's current health
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Request that the ship move itself based on an elapsed ammount of
	 * time
	 * 
	 * @param delta The time that has elapsed since last move (ms)
	 */
	public void move(long delta) {
		// if we're moving left and have reached the left hand side
		// of the screen, don't move
		if ((dx < 0) && (x < 10)) {
			return;
		}
		// if we're moving right and have reached the right hand side
		// of the screen, don't move
		if ((dx > 0) && (x > 750)) {
			return;
		}
		
		super.move(delta);
	}
	
	/**
	 * Notification that the player's ship has collided with something
	 * 
	 * @param other The entity with which the ship has collided
	 */
	public void collidedWith(Entity other) {
		// if its an alien, notify the game that the player
		// is dead
		if (other instanceof AlienEntity) {
			game.notifyDeath();
			sprite = explosionSprite;
		}
	}

	/**
	 * Notify the players ship that is has been hit and to subtract from the
	 * Player's health, if health reaches 0 then notify the Game of a game over
	 */
	public void takeDamage() {
		health--;

		if (health <= 0) {
			game.notifyDeath();
			sprite = explosionSprite;
		}
	}

	/**
	 * Try to fire from the weapon system depending on the current key pressed
	 * 
	 * @param keyCode the value of the key being pressed
	 * 
	 * @return whether a shot has successfully been fired
	 */
	public boolean tryToFire(int keyCode) {
		// if we waited long enough, create the shot entity, and record the time.
		if (keyCode == KeyEvent.VK_SPACE) {
			return weapons.firePrimaryWeapon();
		} else if (keyCode == KeyEvent.VK_A) {
			return weapons.fireSpitShot();
		} else if (keyCode == KeyEvent.VK_S) {
			return weapons.firePiercingShot();
		} else if (keyCode == KeyEvent.VK_D) {
			return weapons.fireRemoteBomb();
		}

		return false;
	}
}