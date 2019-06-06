package org.newdawn.spaceinvaders;

/**
 * A class for managing weapons and weapon types for both players and enemies
 * 
 * @author Chris Hipkins
 */
public class WeaponSystem {
	/** The game in which this WeaponSystem exists */
	private Game game;
	/** The entity that the instance of WeaponSystem is associated with */
	private Entity owner;

	/** The time at which last fired a shot */
	private long lastFire = 0;
	/** The interval between our players primary shot (ms) */
	private long primaryFiringInterval = 500;
	/** The interval between our players primary shot (ms) */
	private long splitShotFiringInterval = 1500;
	/** The interval between our players primary shot (ms) */
	private long piercingShotFiringInterval = 1000;
	/** The interval between our players primary shot (ms) */
	private long remoteBombFiringInterval = 2500;

	/**
	 * Create a new instance of WeaponSystem
	 * 
	 * @param game The game in which the system is being created
	 * @param owner The Entity that will be associated with this instance
	 */
	public WeaponSystem(Game game, Entity owner) {
		this.game = game;
		this.owner = owner;
	}

	/**
	 * Fires a single shot forward from the designated Entity
	 * 
	 * @return if a shot was successfully spawned
	 */
	public boolean firePrimaryWeapon() {
		if (System.currentTimeMillis() - lastFire < primaryFiringInterval) {
			return false;
		}

		ShotEntity shot = new ShotEntity(game,"sprites/shot.gif",owner.getX()+10,owner.getY()-30, 0, 1, true);
		game.addEntity(shot);

		lastFire = System.currentTimeMillis();

		return true;
	}

	/**
	 * Fires 3 shots in a split "W" pattern
	 * 
	 * @return if the shots were successfully spawned
	 */
	public boolean fireSpitShot() {
		if (System.currentTimeMillis() - lastFire < splitShotFiringInterval) {
			return false;
		}

		// Shot fired forward
		ShotEntity shot1 = new ShotEntity(game,"sprites/shot.gif",owner.getX()+10,owner.getY()-30, 0, 1, true);
		game.addEntity(shot1);

		// Shot fired diagonally left
		ShotEntity shot2 = new ShotEntity(game,"sprites/shot.gif",owner.getX()+10,owner.getY()-30, 30, 1, true);
		game.addEntity(shot2);

		// Shot fired diagonally right
		ShotEntity shot3 = new ShotEntity(game,"sprites/shot.gif",owner.getX()+10,owner.getY()-30, -30, 1, true);
		game.addEntity(shot3);

		lastFire = System.currentTimeMillis();

		return true;
	}

	/**
	 * Fire a piercing shot that can hit up to 2 enemies
	 * 
	 * @return if a shot was successfully spawned
	 */
	public boolean firePiercingShot() {
		if (System.currentTimeMillis() - lastFire < piercingShotFiringInterval) {
			return false;
		}

		ShotEntity shot = new ShotEntity(game,"sprites/shot.gif",owner.getX()+10,owner.getY()-30, 0, 2, true);
		game.addEntity(shot);

		lastFire = System.currentTimeMillis();

		return true;
	}

	/**
	 * Fire a shot that can be remotely detonated after being fired
	 * 
	 * @return if a shot was successfully spawned
	 */
	public boolean fireRemoteBomb() {
		if (System.currentTimeMillis() - lastFire < remoteBombFiringInterval) {
			return false;
		}

		ShotEntity shot = new RemoteBombEntity(game, "sprites/shot.gif", owner.getX(), owner.getY(), 0, 6, true);
		game.addEntity(shot);

		lastFire = System.currentTimeMillis();

		return true;
	}
}