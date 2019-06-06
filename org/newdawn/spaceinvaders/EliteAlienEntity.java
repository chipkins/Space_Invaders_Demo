package org.newdawn.spaceinvaders;

import java.util.Random;

/**
 * An entity which represents one of our space invader aliens.
 * 
 * @author Kevin Glass
 */
public class EliteAlienEntity extends AlienEntity {
	/** Alien's current health value */
	private int health = 2;
	/** The time at which last fired a shot */
	private long lastFire = 0;
	/** The rate at which the Alien will fire a shot (ms) */
	private long firingInterval; 
	/** Random number generator used to randomise the firing interval */
	private Random rand = new Random();

	/**
	 * Create a new alien entity
	 * 
	 * @param game The game in which this entity is being created
	 * @param ref The sprite which should be displayed for this alien
	 * @param x The intial x location of this alien
	 * @param y The intial y location of this alient
	 */
	public EliteAlienEntity(Game game,String ref,int x,int y) {
		super(game, ref,x,y);

		firingInterval = (long)(rand.nextInt(2000) + 1000);
	}

	public int getHealth() {
		return health;
	}

	public void takeDamage() {
		health--;
	}

	public void tryToFire() {
		if (System.currentTimeMillis() - lastFire < firingInterval) {
			return;
		}

		ShotEntity shot = new ShotEntity(game,"sprites/alien_shot.gif",this.getX()+15,this.getY()+30, 0, 1, false);
		game.addEntity(shot);

		lastFire = System.currentTimeMillis();
		firingInterval = (long)(rand.nextInt(10000) + 2500);
	}
}