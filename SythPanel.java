/**
 * SythPanel
 * @author Willie, Daniel, Muhammed, Pravinthan
 * @date June 14 2017
 * Class that holds the main game panel
 */

//importing necessary libraries
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SythPanel extends JPanel implements Runnable {

	// variables for the thread
	private Thread thread;
	private boolean running;

	// constants relating to fps and ups
	private static final int TARGET_FPS = 60; // the target FPS
	private static final double TIME_BETWEEN_UPDATES = 1000000000 / TARGET_FPS; // the time between updates in nanoseconds

	// image
	private Image foreground = new ImageIcon("resource/foreground.png").getImage();

	// font
	private Font customFont;

	// 1 = menu, 2 = help, 3 = game, 4 = shop in game, 5 = game over
	private int gameState = 1;

	// positional objects
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private ArrayList<ItemDrop> itemDropList = new ArrayList<ItemDrop>();
	private SwordDrumstick drumstick = new SwordDrumstick(0, 0, 30, 15, 10, 0);
	private SwordLong longSword = new SwordLong(0, 0, 30, 15, 15, 300);
	private SwordGreat greatSword = new SwordGreat(0, 0, 30, 15, 25, 600);
	private SwordGod godSword = new SwordGod(0, 0, 45, 20, 35, 1000);

	// java.util.Timer timers
	private Timer playerAttackCooldownTimer;
	private Timer playerDamageGracePeriodTimer;
	private Timer basicEnemySpawnTimer;
	private Timer strongEnemySpawnTimer;
	private Timer bossEnemySpawnTimer;
	private Timer itemDropEnhancementTimer;
	private Timer itemDropPickUpDelayTimer;
	private Timer roundWaitTimer;

	// round related variables
	private int roundNumber = 0;
	private int basicEnemyThisRound = 0;
	private int strongEnemyThisRound = 0;
	private int bossEnemyThisRound = 0;
	private int basicEnemySpawned = 0;
	private int strongEnemySpawned = 0;
	private int bossEnemySpawned = 0;
	private int zombiesToKill;
	private int totalZombiesKilled;

	// flag for when the rounds are changing
	private boolean settingNextRound = false;

	// variables for spawning enemies
	private int ySpawnEnemy;
	private int xSpawnEnemy;

	//Constructor
	public SythPanel() {
		setDoubleBuffered(true); // double buffering for smoother experience

		initTimers(); // add timers
		initFont(); // add custom font 
		addMouseListener(new MouseListen()); // add our mouse listener
		addKeyListener(new KeyListen()); // add our key listener

		runMusic(); // play music
		start(); // start game
	}// end of constructor

	/**
	 * initTimers
	 * Method that initialises timers used by the program
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void initTimers() {
		playerAttackCooldownTimer = new Timer();
		playerDamageGracePeriodTimer = new Timer();
		basicEnemySpawnTimer = new Timer();
		strongEnemySpawnTimer = new Timer();
		bossEnemySpawnTimer = new Timer();
		itemDropEnhancementTimer = new Timer();
		itemDropPickUpDelayTimer = new Timer();
		roundWaitTimer = new Timer();
	}// end of initTimers

	/**
	 * initFont
	 * Method that creates a custom font for the game
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void initFont() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("resource/zombieFont.ttf"));
			ge.registerFont(customFont);
		} catch (Exception e) { // Runs if font does not exist in folder
			e.printStackTrace();
		}// end of try catch
	}// end of initFont

	/**
	 * scheduleEnemyTimers
	 * Method that schedules timers used for spawning enemies
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void scheduleEnemyTimers() {
		scheduleBasicEnemySpawnTimer();
		scheduleStrongEnemySpawnTimer();
		scheduleBossEnemySpawnTimer();
	}// end of scheduleEnemyTimers

	/**
	 * generateCoordinatesForEnemy
	 * Method that generates coordinates to spawn an enemy at
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void generateCoordinatesForEnemy() {
		//Choosing which side to spawn enemy on
		int randomSide = (int) (Math.random() * 2 + 1);

		if (randomSide >= 2) {
			xSpawnEnemy = SythFrame.WIDTH + 100;
		} else {
			xSpawnEnemy = -100;
		}

		//Choosing which y coordinate to spawn enemy at
		ySpawnEnemy = (int) (Math.random() * 300 + SythFrame.HEIGHT / 2 + 100);

		if (ySpawnEnemy > 700) {
			ySpawnEnemy -= 100;
		}
	}// end of generateCoordinatesForEnemy

	/**
	 * schedulePlayerAttackCooldownTimer
	 * Method that applies a cooldown when a player attacks
	 * Returns nothing
	 * @param player - Player that attacked
	 * @return void
	 */
	private void schedulePlayerAttackCooldownTimer(Player player) {
		playerAttackCooldownTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				player.setAttackAllowed(true); // allows player to attack again
				setWeaponOrientation(player, false);
			}
		}, 500);
	}// end of schedulePlayerAttackCooldownTimer

	/**
	 * schedulePlayerDamageGracePeriodTimer
	 * Method that applies a grace period when a player is attacked
	 * Returns nothing
	 * @param player - Player that was attacked
	 * @return void
	 */
	private void schedulePlayerDamageGracePeriodTimer(Player player) {
		playerDamageGracePeriodTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				player.setDamageAllowed(true); // allows player to be damaged again
			}
		}, 1000);
	}// end of schedulePlayerDamageGracePeriodTimer

	/**
	 * scheduleBasicEnemySpawnTimer
	 * Method that spawns a number of basic enemies during a round at a certain rate
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void scheduleBasicEnemySpawnTimer() {
		basicEnemySpawnTimer = new Timer();
		basicEnemySpawnTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (basicEnemySpawned == basicEnemyThisRound) { // runs if enough basic enemies for the round have been spawned
					basicEnemySpawnTimer.cancel();
				} else {
					//Creating new enemy
					generateCoordinatesForEnemy();
					enemyList.add(new EnemyBasic(xSpawnEnemy, ySpawnEnemy, 50, 50, 100, 100, 2, 2, 1, false));
					basicEnemySpawned++;
				}
			}
		}, 0, 7000);
	}// end of scheduleBasicEnemySpawnTimer

	/**
	 * scheduleStrongEnemySpawnTimer
	 * Method that spawns a number of strong enemies during a round at a certain rate
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void scheduleStrongEnemySpawnTimer() {
		strongEnemySpawnTimer = new Timer();
		strongEnemySpawnTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (strongEnemySpawned == strongEnemyThisRound) { // runs if enough strong enemies for the round have been spawned
					strongEnemySpawnTimer.cancel();
				} else {
					//Creating new enemy
					generateCoordinatesForEnemy();
					enemyList.add(new EnemyStrong(xSpawnEnemy, ySpawnEnemy, 50, 50, 150, 150, 5, 2, 1, false));
					strongEnemySpawned++;
				}
			}
		}, 0, 8000);
	}// end of scheduleStrongEnemySpawnTimer

	/**
	 * scheduleBossEnemySpawnTimer
	 * Method that spawns a number of boss enemies during a round at a certain rate
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void scheduleBossEnemySpawnTimer() {
		bossEnemySpawnTimer = new Timer();
		bossEnemySpawnTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (bossEnemySpawned == bossEnemyThisRound) { // runs if enough boss enemies for the round have been spawned
					bossEnemySpawnTimer.cancel();
				} else {
					//Creating new enemy
					generateCoordinatesForEnemy();
					enemyList.add(new EnemyBoss(xSpawnEnemy, ySpawnEnemy, 100, 100, 300, 300, 15, 2, 1, false));
					bossEnemySpawned++;
				}
			}
		}, 0, 15000);
	}// end of scheduleBossEnemySpawnTimer

	/**
	 * scheduleItemDropEnhancementTimer
	 * Method that times the duration of a buff and resets player status when it ends
	 * Returns nothing
	 * @param player - Player receiving the buff
	 * @param itemDrop - Item that buffs the player
	 * @return void
	 */
	private void scheduleItemDropEnhancementTimer(Player player, ItemDrop itemDrop) {
		itemDropEnhancementTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				//Reset player status
				if (itemDrop instanceof ItemDropExtraStrength) {
					player.setAttackMultiplier(1);
				} else if (itemDrop instanceof ItemDropInvincibility) {
					player.setInvincible(false);
				}
			}
		}, 5000);
	}// end of scheduleItemDropEnhancementTimer

	/**
	 * scheduleItemDropPickUpDelayTimer
	 * Method that allows an item to be picked up after a delay
	 * Returns nothing
	 * @param player - Player picking up the item
	 * @param itemDrop - Item to be picked up
	 * @return void
	 */
	private void scheduleItemDropPickUpDelayTimer(Player player, ItemDrop itemDrop) {
		itemDropPickUpDelayTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				itemDrop.setObtainable(true);
			}
		}, 750);
	}// end of scheduleItemDropPickUpDelayTimer

	/**
		 * scheduleNextRoundTimer
		 * Method that changes/resets variables for the next wave of zombies
		 * Returns nothing
		 * @param nothing
		 * @return void
		 */
	private void scheduleNextRoundTimer() {
		roundWaitTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				roundNumber++;
				zombiesToKill = 0;
				basicEnemySpawned = 0;
				strongEnemySpawned = 0;
				bossEnemySpawned = 0;
				roundChange();
				settingNextRound = false;
			}
		}, 5000);
	} // end of scheduleNextRoundTimer

	/**
	 * roundChange
	 * Method that prepares the number of zombies to be killed for the next wave
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void roundChange() {
		//Determine how many enemies of each type will be spawned per round
		basicEnemyThisRound = (int) (roundNumber + Math.floor(Math.pow(roundNumber, 0.9))) * playerList.size();
		strongEnemyThisRound = (int) (roundNumber + Math.floor(Math.pow(roundNumber, 0.9)) - 20) * playerList.size();

		if (strongEnemyThisRound < 0) {
			strongEnemyThisRound = 0;
		}

		if (roundNumber % 10 == 0){
			bossEnemyThisRound = roundNumber / 10 * playerList.size();
		} else {
			bossEnemyThisRound = 0;
		}

		zombiesToKill = basicEnemyThisRound + strongEnemyThisRound + bossEnemyThisRound; // total number of zombies to kill in round

		scheduleEnemyTimers(); // start enemy spawn timers
	} // end of roundChange

	/**
	 * checkForNextRound
	 * Method used to check if the wave is over (all zombies are dead)
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void checkForNextRound() {
		if (zombiesToKill == 0) { // runs if all zombies for round are killed
			scheduleNextRoundTimer(); // runs next round change timer
			settingNextRound = true;
		}
	}// end of checkForNextRound

	/**
	 * runMusic
	 * Method that plays music during the game
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void runMusic() {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("resource/music.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) { // runs if music does not exist
			e.printStackTrace();
		}
	}// end of runMusic

	/**
	 * start
	 * Method that starts the thread
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}// end of start

	/**
	 * run
	 * Method that calls for updates, repaints the screen and maintains FPS
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	@Override
	public void run() {
		// this code maintains the UPS and FPS at 60
		double lastUpdate = System.nanoTime(); // the last time the window updated
		double delta = 0; // the counted time between updates
		double now;
		while (running) {
			now = System.nanoTime();
			delta += (now - lastUpdate) / TIME_BETWEEN_UPDATES;
			lastUpdate = now;

			// if delta is greater or equal to 1, then we need to update the game logic
			while (delta >= 1) {
				if (gameState == 3 || gameState == 4) { // if game state is 3 or 4, update the game
					update();
				}
				
				delta--;
			}

			repaint(); // repaint the screen
		}
	} // end of run

	/**
	 * update
	 * Method that updates the game upon input or interaction
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void update() {
		followPlayer(); // update zombie position
		keyUpdate(); // update player position

		if (!settingNextRound) {
			checkForNextRound(); // runs method that starts next round change timer
		}

		//Stops player from moving past boundaries
		for (Player player : playerList) {
			// border for the x axis
			if (player.getxPosition() <= 0) {
				player.setxPosition(0);
			} else if (player.getxPosition() + player.getWidth() + 8 >= SythFrame.WIDTH) {
				player.setxPosition(SythFrame.WIDTH - player.getWidth() - 8);
			}

			// border for the y axis
			if (player.getyPosition() <= 498) {
				player.setyPosition(498);
			} else if (player.getyPosition() + player.getHeight() + 30 >= SythFrame.HEIGHT) {
				player.setyPosition(SythFrame.HEIGHT - player.getHeight() - 30);
			}

			// player hitting item drops
			for (int i = 0; i < itemDropList.size(); i++) {
				ItemDrop itemDrop = itemDropList.get(i);
				if (player.getHitBox().intersects(itemDrop.getHitBox()) && itemDrop.isObtainable() && !player.isDead()) {
					if (itemDrop instanceof ItemDropExtraStrength) {
						player.setAttackMultiplier(2);
						scheduleItemDropEnhancementTimer(player, itemDrop);
					} else if (itemDrop instanceof ItemDropInvincibility) {
						player.setInvincible(true);
						scheduleItemDropEnhancementTimer(player, itemDrop);
					} else if (itemDrop instanceof ItemDropMaxHealth) {
						player.setHealth(player.getMaxHealth());
					} else if (itemDrop instanceof ItemDropMoney) {
						player.setMoney(player.getMoney() + 150);
					} else if (itemDrop instanceof ItemDropNuke) {
						totalZombiesKilled += enemyList.size();
						enemyList.clear();
						zombiesToKill = 0;
					}

					itemDropList.remove(i);
				}
			}

			// enemy hitting player
			if (player.isDamageAllowed() && !player.isInvincible() && !player.isDead()) {
				for (Enemy enemy : enemyList) {
					if (enemy.getHitBox().intersects(player.getHitBox())) {
						player.setHealth(player.getHealth() - enemy.getAttack());
						if (player.getHealth() <= 0) {
							player.setDead(true);
						} else {
							player.setDamageAllowed(false);
							schedulePlayerDamageGracePeriodTimer(player);
						}
					}
				}
			}
		}

		//Game over state
		if (playerList.size() == 2) { // if two player version of the game
			if (playerList.get(0).isDead() && playerList.get(1).isDead()) {
				gameState = 5;
			}
		} else if (playerList.size() == 1) { // if one player version of the game
			if (playerList.get(0).isDead()) {
				gameState = 5;
			}
		}
	} // end of update

	/**
	 * keyUpdate
	 * Method that updates the game when a key is pressed
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void keyUpdate() {
		for (Player player : playerList) {
			if (!player.isDead()) {
				if (player.isUp()) { // move up
					player.setyPosition(player.getyPosition() - player.getySpeed());
				}

				if (player.isDown()) { // move down
					player.setyPosition(player.getyPosition() + player.getySpeed());
				}

				if (player.isLeft()) { // move left
					player.setxPosition(player.getxPosition() - player.getxSpeed());
					player.setFacingRight(false);
				}

				if (player.isRight()) { // move right
					player.setxPosition(player.getxPosition() + player.getxSpeed());
					player.setFacingRight(true);
				}

				setWeaponPosition(player); // reset weapon position after moving player
			}

			//Player is attacking
			if (player.isSpace() && player.isAttackAllowed() && !player.isDead()) {
				setWeaponOrientation(player, true); // sets the weapon in attacking position
				for (int i = 0; i < enemyList.size(); i++) {
					Enemy enemy = enemyList.get(i);
					if (enemy.getHitBox().intersects(player.getSelectedWeapon().getHitBox())) {
						enemy.setHealth(enemy.getHealth() - player.getSelectedWeapon().getAddAttackValue() * player.getAttackMultiplier());
						if (enemy.getHealth() <= 0) {
							// drop a random item 15% of the time
							Random r = new Random();
							double num = r.nextDouble();
							if (num >= 0.6) { // 40% drop rate for all items combined
								ItemDrop itemDrop = null;
								if (num >= 0.6 && num < 0.7) { // 10% drop rate
									itemDrop = new ItemDropExtraStrength(enemy.getxPosition(), enemy.getyPosition(), 50, 50, false);
								} else if (num >= 0.7 && num < 0.75) { // 5% drop rate
									itemDrop = new ItemDropInvincibility(enemy.getxPosition(), enemy.getyPosition(), 50, 50, false);
								} else if (num >= 0.75 && num < 0.91) { // 16% drop rate
									itemDrop = new ItemDropMaxHealth(enemy.getxPosition(), enemy.getyPosition(), 50, 50, false);
								} else if (num >= 0.91 && num < 0.95) { // 4% drop rate
									itemDrop = new ItemDropMoney(enemy.getxPosition(), enemy.getyPosition(), 50, 50, false);
								} else if (num >= 0.95 && num < 1) { // 5% drop rate
									itemDrop = new ItemDropNuke(enemy.getxPosition(), enemy.getyPosition(), 50, 50, false);
								}

								itemDropList.add(itemDrop);
								scheduleItemDropPickUpDelayTimer(player, itemDrop);
							}

							// gain different amount of money based on enemy type
							if (enemy instanceof EnemyBasic) {
								player.setMoney(player.getMoney() + 30);
							} else if (enemy instanceof EnemyStrong) {
								player.setMoney(player.getMoney() + 50);
							} else if (enemy instanceof EnemyBoss) {
								player.setMoney(player.getMoney() + 100);
							}

							enemyList.remove(enemy); // remove the enemy from the list
							zombiesToKill--; // decrement the amount of zombies to kill
							totalZombiesKilled++; // increment total number of zombies killed
						}
					}
				}

				player.setAttackAllowed(false);
				schedulePlayerAttackCooldownTimer(player); // put attack on cooldown
			}
		}
	} // end of keyUpdate

	/**
	 * setWeaponPosition
	 * Method that sets a weapon's position relative to a player's movement
	 * Returns nothing
	 * @param player - Player to have its position referenced
	 * @return void
	 */
	private void setWeaponPosition(Player player) {
		if (player.isFacingRight()) { // player facing right
			player.getSelectedWeapon().setxPosition(player.getxPosition() + player.getWidth() - player.getSelectedWeapon().getWidth());
		} else { // player facing left
			player.getSelectedWeapon().setxPosition(player.getxPosition());
		}
		player.getSelectedWeapon().setyPosition(player.getyPosition() + player.getHeight() / 2);
	} // end of setWeaponPosition

	/**
	 * setWeaponOrientation
	 * Method that sets a weapon's orientation when a player attacks
	 * Returns nothing
	 * @param player - Player that is attacking
	 * @param attack - Boolean that determines whether player is attacking or not
	 * @return void
	 */
	private void setWeaponOrientation(Player player, boolean attack) {
		if (player.isFacingRight()) { // player facing right
			if (attack) { // player is attacking
				player.getSelectedWeapon().setxPosition(player.getSelectedWeapon().getxPosition() + player.getSelectedWeapon().getWidth());
			} else {
				player.getSelectedWeapon().setxPosition(player.getSelectedWeapon().getxPosition() - player.getSelectedWeapon().getWidth());
			}
		} else { // player faing left
			if (attack) { // player is attacking
				player.getSelectedWeapon().setxPosition(player.getSelectedWeapon().getxPosition() - player.getSelectedWeapon().getWidth());
			} else {
				player.getSelectedWeapon().setxPosition(player.getSelectedWeapon().getxPosition() + player.getSelectedWeapon().getWidth());
			}
		}
	}// end of setWeaponOrientation

	/**
	 * followPlayer
	 * Method used for zombies to follow players around
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void followPlayer() {
		for (int i = 0; i < enemyList.size(); i++) {
			Enemy enemy = enemyList.get(i);
			
			// algorithm to decide who to follow
			Player player = null;
			if (playerList.size() == 1) {
				player = playerList.get(0);
			} else if (playerList.size() == 2) {
				if (i % 2 == 0) {
					if (!playerList.get(0).isDead()) {
						player = playerList.get(0);
					} else if (!playerList.get(1).isDead()) {
						player = playerList.get(1);
					}
				} else {
					if (!playerList.get(1).isDead()) {
						player = playerList.get(1);
					} else if (!playerList.get(0).isDead()) {
						player = playerList.get(0);
					}
				}
			}

			if (player != null) {
				int xDifference = enemy.getxPosition() - player.getxPosition();
				int yDifference = enemy.getyPosition() - player.getyPosition();
				double distance = Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));

				//Changing position of zombie and orientation based on its position relative to player
				if (distance <= 300) {
					if (xDifference >= player.getWidth()) {
						enemy.setxPosition(enemy.getxPosition() - enemy.getxSpeed());
						enemy.setFacingRight(true);
					}

					if (xDifference + enemy.getWidth() <= 0) {
						enemy.setxPosition(enemy.getxPosition() + enemy.getxSpeed());
						enemy.setFacingRight(false);
					}

					if (yDifference > 0) {
						enemy.setyPosition(enemy.getyPosition() - enemy.getySpeed());
					}

					if (yDifference < 0) {
						enemy.setyPosition(enemy.getyPosition() + enemy.getySpeed());
					}
				} else {
					if (xDifference >= player.getWidth()) {
						enemy.setxPosition(enemy.getxPosition() - enemy.getxSpeed());
						enemy.setFacingRight(true);
					}

					if (xDifference + enemy.getWidth() <= 0) {
						enemy.setxPosition(enemy.getxPosition() + enemy.getxSpeed());
						enemy.setFacingRight(false);
					}
				}
			}
		}
	}// end of followPlayer

	/**
	 * stopGame
	 * Method that stops all processes in game
	 * Returns nothing
	 * @param nothing
	 * @return void
	 */
	private void stopGame() {
		// clear the lists
		playerList.clear();
		enemyList.clear();
		itemDropList.clear();

		// cancel all timers
		playerAttackCooldownTimer.cancel();
		playerDamageGracePeriodTimer.cancel();
		basicEnemySpawnTimer.cancel();
		strongEnemySpawnTimer.cancel();
		bossEnemySpawnTimer.cancel();
		itemDropEnhancementTimer.cancel();
		itemDropPickUpDelayTimer.cancel();
		roundWaitTimer.cancel();

		// reset all round related variables
		roundNumber = 0;
		zombiesToKill = 0;
		totalZombiesKilled = 0;

		// initialise the timers since we cancelled all of them
		initTimers();
	}// end of stopGame

	/**
	 * paintComponent
	 * Method that draws graphics in the panel
	 * Returns nothing
	 * @param g - Graphics used for drawing
	 * @return void
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // invoke the super method paintComponent
		if (gameState == 1) {
			new ScreenMenu().draw(g); // draw the menu screen
		} else if (gameState == 2) {
			new ScreenHelp().draw(g); // draw the help screen
		} else if (gameState == 3) {
			new ScreenGame().draw(g); // draw the game screen

			// draw enemies
			for (int i = 0; i < enemyList.size(); i++) {
				Enemy enemy = enemyList.get(i);
				if (enemy.isFacingRight()) {
					enemy.draw(g, enemy.getxPosition() + enemy.getWidth(), enemy.getyPosition(), -enemy.getWidth(), enemy.getHeight());
				} else {
					enemy.draw(g, enemy.getxPosition(), enemy.getyPosition(), enemy.getWidth(), enemy.getHeight());
				}

				// draw the enemy health bars only if they have hit
				if (enemy.getHealth() < enemy.getMaxHealth()){
					g.setColor(Color.BLACK);
					g.fillRect(enemy.getxPosition(), enemy.getyPosition() - 11, enemy.getWidth() + 2, 12);
					g.setColor(Color.GREEN);
					g.fillRect(enemy.getxPosition() + 1, enemy.getyPosition() - 10, (int) (((double)enemy.getHealth()) / enemy.getMaxHealth() * enemy.getWidth()), 10);
				}
			}

			// draw item drops
			for (int i = 0; i < itemDropList.size(); i++) {
				ItemDrop itemDrop = itemDropList.get(i);
				itemDrop.draw(g, itemDrop.getxPosition(), itemDrop.getyPosition(), itemDrop.getWidth(), itemDrop.getHeight());
			}

			// draw the players, weapons, health bars, invincibility indicators, extra strength indicators, money counts
			for (int i = 0; i < playerList.size(); i++) {
				Player player = playerList.get(i);

				// only draw the player related objects if the player is not dead
				if (!player.isDead()) {
					// draw the player
					if (player.isFacingRight()) {
						player.draw(g, player.getxPosition(), player.getyPosition(), player.getWidth(), player.getHeight());
					} else {
						player.draw(g, player.getxPosition() + player.getWidth(), player.getyPosition(), -player.getWidth(), player.getHeight());
					}

					// draw the weapon
					Rectangle tempWeaponRectangle = determineWeaponRectangle(player);
					player.getSelectedWeapon().draw(g, tempWeaponRectangle.x, tempWeaponRectangle.y, tempWeaponRectangle.width, tempWeaponRectangle.height);

					// draw health bar
					g.setColor(Color.BLACK);
					g.fillRect(player.getxPosition(), player.getyPosition() - 11, player.getWidth() - 8, 12);
					g.setColor(Color.GREEN);
					g.fillRect(player.getxPosition() + 1, player.getyPosition() - 10, (int) (((double) player.getHealth()) / player.getMaxHealth() * (player.getWidth() - 10)), 10);

					// if the player is invincible, indicate the user that the player is invincible
					if (player.isInvincible()) {
						ItemDrop itemDrop = new ItemDropInvincibility(player.getxPosition(), player.getyPosition() - 30, 20, 20, false);
						itemDrop.draw(g, itemDrop.getxPosition(), itemDrop.getyPosition(), itemDrop.getWidth(), itemDrop.getHeight());
					}

					// if the player has extra strength, indicate the user that the player has extra strength
					if (player.getAttackMultiplier() == 2) {
						ItemDrop itemDrop = new ItemDropExtraStrength(player.getxPosition() + 20, player.getyPosition() - 30, 25, 25, false);
						itemDrop.draw(g, itemDrop.getxPosition(), itemDrop.getyPosition(), itemDrop.getWidth(), itemDrop.getHeight());
					}
				}

				// draw money for both players
				g.setColor(Color.BLACK);
				customFont = customFont.deriveFont(1, 30);
				g.setFont(customFont);
				g.drawString("Player " + (i + 1), 20, - 50 + 90 * (i + 1));
				customFont = customFont.deriveFont(1, 80);
				g.setFont(customFont);
				g.drawString("$" + String.valueOf(player.getMoney()), 20, 10 + 90 * (i + 1));

				// draw round related strings
				customFont = customFont.deriveFont(1, 40);
				g.setFont(customFont);
				g.drawString("Round " + String.valueOf(roundNumber), 200, 45);
				g.drawString(String.valueOf(zombiesToKill) + " zombies remaining", 200, 85);
			}
		} else if (gameState == 4) {
			new ScreenShop().draw(g); // draw the shop screen

			Player player = findShopOpenedPlayer();
			Weapon weapon = player.getSelectedWeapon();

			g.setColor(Color.BLACK);
			customFont = customFont.deriveFont(1, 80);
			g.setFont(customFont);

			//drawing cost of weapons
			g.drawString("$" + drumstick.getStoreCost(), 75, 640);
			g.drawString("$" + longSword.getStoreCost(), 320, 640);
			g.drawString("$" + greatSword.getStoreCost(), 640, 640);
			g.drawString("$" + godSword.getStoreCost(), 950, 640);

			customFont = customFont.deriveFont(1, 50);
			g.setFont(customFont);

			//drawing text for buttons
			if (weapon instanceof SwordDrumstick) { // drumstick is equipped
				g.drawString("Equipped", ScreenShop.getDrumstickSwordEquipButton().x - 20, ScreenShop.getDrumstickSwordEquipButton().y + ScreenShop.getDrumstickSwordEquipButton().height);

				if (player.getPurchasedWeapons().contains(longSword)) {
					g.drawString("Equip", ScreenShop.getLongSwordEquipButton().x, ScreenShop.getLongSwordEquipButton().y + ScreenShop.getLongSwordEquipButton().height);
				} else {
					g.drawString("Buy", ScreenShop.getLongSwordEquipButton().x + 15, ScreenShop.getLongSwordEquipButton().y + ScreenShop.getLongSwordEquipButton().height);
				}

				if (player.getPurchasedWeapons().contains(greatSword)) {
					g.drawString("Equip", ScreenShop.getGreatSwordEquipButton().x, ScreenShop.getGreatSwordEquipButton().y + ScreenShop.getGreatSwordEquipButton().height);
				} else {
					g.drawString("Buy", ScreenShop.getGreatSwordEquipButton().x + 30, ScreenShop.getGreatSwordEquipButton().y + ScreenShop.getGreatSwordEquipButton().height);
				}

				if (player.getPurchasedWeapons().contains(godSword)) {
					g.drawString("Equip", ScreenShop.getGodSwordEquipButton().x, ScreenShop.getGodSwordEquipButton().y + ScreenShop.getGodSwordEquipButton().height);					
				} else {
					g.drawString("Buy", ScreenShop.getGodSwordEquipButton().x + 30, ScreenShop.getGodSwordEquipButton().y + ScreenShop.getGodSwordEquipButton().height);
				}
			} else if (weapon instanceof SwordLong) { // long sword is equipped
				g.drawString("Equip", ScreenShop.getDrumstickSwordEquipButton().x, ScreenShop.getDrumstickSwordEquipButton().y + ScreenShop.getDrumstickSwordEquipButton().height);

				g.drawString("Equipped", ScreenShop.getLongSwordEquipButton().x - 20, ScreenShop.getLongSwordEquipButton().y + ScreenShop.getLongSwordEquipButton().height);

				if (player.getPurchasedWeapons().contains(greatSword)) {
					g.drawString("Equip", ScreenShop.getGreatSwordEquipButton().x, ScreenShop.getGreatSwordEquipButton().y + ScreenShop.getGreatSwordEquipButton().height);
				} else {
					g.drawString("Buy", ScreenShop.getGreatSwordEquipButton().x + 30, ScreenShop.getGreatSwordEquipButton().y + ScreenShop.getGreatSwordEquipButton().height);
				}

				if (player.getPurchasedWeapons().contains(godSword)) {
					g.drawString("Equip", ScreenShop.getGodSwordEquipButton().x, ScreenShop.getGodSwordEquipButton().y + ScreenShop.getGodSwordEquipButton().height);					
				} else {
					g.drawString("Buy", ScreenShop.getGodSwordEquipButton().x + 30, ScreenShop.getGodSwordEquipButton().y + ScreenShop.getGodSwordEquipButton().height);
				}
			} else if (weapon instanceof SwordGreat) { // great sword is equipped
				g.drawString("Equip", ScreenShop.getDrumstickSwordEquipButton().x, ScreenShop.getDrumstickSwordEquipButton().y + ScreenShop.getDrumstickSwordEquipButton().height);

				if (player.getPurchasedWeapons().contains(longSword)) {
					g.drawString("Equip", ScreenShop.getLongSwordEquipButton().x, ScreenShop.getLongSwordEquipButton().y + ScreenShop.getLongSwordEquipButton().height);
				} else {
					g.drawString("Buy", ScreenShop.getLongSwordEquipButton().x + 15, ScreenShop.getLongSwordEquipButton().y + ScreenShop.getLongSwordEquipButton().height);
				}

				g.drawString("Equipped", ScreenShop.getGreatSwordEquipButton().x - 20, ScreenShop.getGreatSwordEquipButton().y + ScreenShop.getGreatSwordEquipButton().height);

				if (player.getPurchasedWeapons().contains(godSword)) {
					g.drawString("Equip", ScreenShop.getGodSwordEquipButton().x, ScreenShop.getGodSwordEquipButton().y + ScreenShop.getGodSwordEquipButton().height);					
				} else {
					g.drawString("Buy", ScreenShop.getGodSwordEquipButton().x + 30, ScreenShop.getGodSwordEquipButton().y + ScreenShop.getGodSwordEquipButton().height);
				}
			} else if (weapon instanceof SwordGod) { // god sword is equipped
				g.drawString("Equip", ScreenShop.getDrumstickSwordEquipButton().x, ScreenShop.getDrumstickSwordEquipButton().y + ScreenShop.getDrumstickSwordEquipButton().height);

				if (player.getPurchasedWeapons().contains(longSword)) {
					g.drawString("Equip", ScreenShop.getLongSwordEquipButton().x, ScreenShop.getLongSwordEquipButton().y + ScreenShop.getLongSwordEquipButton().height);
				} else {
					g.drawString("Buy", ScreenShop.getLongSwordEquipButton().x + 15, ScreenShop.getLongSwordEquipButton().y + ScreenShop.getLongSwordEquipButton().height);
				}

				if (player.getPurchasedWeapons().contains(greatSword)) {
					g.drawString("Equip", ScreenShop.getGreatSwordEquipButton().x, ScreenShop.getGreatSwordEquipButton().y + ScreenShop.getGreatSwordEquipButton().height);
				} else {
					g.drawString("Buy", ScreenShop.getGreatSwordEquipButton().x + 30, ScreenShop.getGreatSwordEquipButton().y + ScreenShop.getGreatSwordEquipButton().height);
				}

				g.drawString("Equipped", ScreenShop.getGodSwordEquipButton().x - 20, ScreenShop.getGodSwordEquipButton().y + ScreenShop.getGodSwordEquipButton().height);					
			}
		} else if (gameState == 5) {
			new ScreenGameOver().draw(g); // draw the game over screen

			g.setColor(Color.BLACK);
			customFont = customFont.deriveFont(1, 30);
			g.setFont(customFont);

			g.drawString("You survived " + String.valueOf(roundNumber - 1) + " waves!", 100, 600);
			g.drawString("You killed " + String.valueOf(totalZombiesKilled) + " zombies!", 750, 600);
		}

		// draw the foreground
		if (gameState != 4) {
			g.drawImage(foreground, 0, SythFrame.HEIGHT - 125, SythFrame.WIDTH, 100, this);			
		}
	}// end of paintComponent

	/**
	 * findShopOpenedPlayer
	 * Method that determines which player opened the shop
	 * Returns identity of player
	 * @param nothing
	 * @return - Player who opened shop
	 */
	private Player findShopOpenedPlayer() {
		for (int i = 0; i < playerList.size(); i++) {
			Player playerTemp = playerList.get(i);
			if (playerTemp.isShopOpened()) {
				return playerTemp;
			}
		}

		return null;
	}// end of findShopOpenedPlayer

	/**
	 * determineWeaponRectangle
	 * Method that determines rectangle boundaries of a weapon
	 * Returns a rectangle outlining weapon boundaries
	 * @param player - Player holding the weapon
	 * @return - Rectangle with weapon boundaries
	 */
	private Rectangle determineWeaponRectangle(Player player) {
		if (player.isFacingRight()) { // player and weapon facing right
			return new Rectangle(player.getxPosition() + player.getWidth() - 19, player.getyPosition() + player.getHeight() / 2 - player.getSelectedWeapon().getWidth() + 10, player.getSelectedWeapon().getHeight(), player.getSelectedWeapon().getWidth());
		} else { // player and weapon facing left
			return new Rectangle(player.getxPosition() + 4, player.getyPosition() + player.getHeight() / 2 - player.getSelectedWeapon().getWidth() + 10, player.getSelectedWeapon().getHeight(), player.getSelectedWeapon().getWidth());
		}
	}// end of determineWeaponRectangle

	/**
	 * MouseListen
	 * @author Pravinthan
	 * @date June 14 2017
	 * Class that handles mouse input
	 */
	private class MouseListen extends MouseAdapter {
		
		/**
		 * mouseClicked
		 * Method that performs actions initiated by mouse
		 * Returns nothing
		 * @param MouseEvent e - Represents mouse action
		 * @return void
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (gameState == 1) {
				if (ScreenMenu.getStartButton().contains(e.getPoint())) {
					String[] gameTypeOptions = {"One Player", "Two Players"};
					String gameType = (String) JOptionPane.showInputDialog(SythPanel.this, "One player or two players?", "Choose one", JOptionPane.QUESTION_MESSAGE, null, gameTypeOptions, gameTypeOptions[0]);
					if (gameType != null) {
						if (gameType.equals("One Player")) {
							// add the player to the list
							playerList.add(new Player(SythFrame.WIDTH / 2, SythFrame.HEIGHT / 2 + 150, 50, 50, 100, 100, 0, 5, 3, false, true, true, false, 1, 0, (Weapon) drumstick, new ArrayList<Weapon>(), false, false, false, false, false, false, false));

							// set the weapon to a drumstick
							for (int i = 0; i < playerList.size(); i++) {
								ArrayList<Weapon> tempList = playerList.get(i).getPurchasedWeapons();
								tempList.add(drumstick);
								playerList.get(i).setPurchasedWeapons(tempList);
							}

							gameState = 3; // change screen to the game screen
						} else if (gameType.equals("Two Players")) {
							// add the players to the list
							playerList.add(new Player(SythFrame.WIDTH / 2, SythFrame.HEIGHT / 2 + 150, 50, 50, 100, 100, 0, 5, 3, false, true, true, false, 1, 0, (Weapon) drumstick, new ArrayList<Weapon>(), false, false, false, false, false, false, false));
							playerList.add(new Player(SythFrame.WIDTH / 2 + 150, SythFrame.HEIGHT / 2 + 250, 50, 50, 100, 100, 0, 5, 3, false, true, true, false, 1, 0, (Weapon) drumstick, new ArrayList<Weapon>(), false, false, false, false, false, false, false));

							// set the weapon to a drumstick
							for (int i = 0; i < playerList.size(); i++) {
								ArrayList<Weapon> tempList = playerList.get(i).getPurchasedWeapons();
								tempList.add(drumstick);
								playerList.get(i).setPurchasedWeapons(tempList);
							}

							gameState = 3; // change screen to the game screen
						}
					}
				} else if (ScreenMenu.getHelpButton().contains(e.getPoint())) {
					gameState = 2;
				} else if (ScreenMenu.getExitButton().contains(e.getPoint())) {
					System.exit(0); // stops all threads
				}
			} else if (gameState == 2) {
				if (ScreenHelp.getBackButton().contains(e.getPoint())) {
					gameState = 1;
				}
			} else if (gameState == 4) {
				Player player = findShopOpenedPlayer();

				if (ScreenShop.getBackButton().contains(e.getPoint())) {
					gameState = 3;

					// closes all shops flags
					for (int i = 0; i < playerList.size(); i++) {
						playerList.get(i).setShopOpened(false);
					}
				} else if (ScreenShop.getDrumstickSwordEquipButton().contains(e.getPoint())) {
					if (player.getPurchasedWeapons().contains(drumstick)) {
						player.setSelectedWeapon(drumstick);
					}
				} else if (ScreenShop.getLongSwordEquipButton().contains(e.getPoint())) {
					if (player.getPurchasedWeapons().contains(longSword)) {
						player.setSelectedWeapon(longSword);
					} else {
						if (player.getMoney() >= longSword.getStoreCost()) {
							player.setMoney(player.getMoney() - longSword.getStoreCost());
							player.setSelectedWeapon(longSword);
							ArrayList<Weapon> tempList = player.getPurchasedWeapons();
							tempList.add(longSword);
							player.setPurchasedWeapons(tempList);
						}
					}
				} else if (ScreenShop.getGreatSwordEquipButton().contains(e.getPoint())) {
					if (player.getPurchasedWeapons().contains(greatSword)) {
						player.setSelectedWeapon(greatSword);
					} else {
						if (player.getMoney() >= greatSword.getStoreCost()) {
							player.setMoney(player.getMoney() - greatSword.getStoreCost());
							player.setSelectedWeapon(greatSword);
							ArrayList<Weapon> tempList = player.getPurchasedWeapons();
							tempList.add(greatSword);
							player.setPurchasedWeapons(tempList);
						}
					}
				} else if (ScreenShop.getGodSwordEquipButton().contains(e.getPoint())) {
					if (player.getPurchasedWeapons().contains(godSword)) {
						player.setSelectedWeapon(godSword);
					} else {
						if (player.getMoney() >= godSword.getStoreCost()) {
							player.setMoney(player.getMoney() - godSword.getStoreCost());
							player.setSelectedWeapon(godSword);
							ArrayList<Weapon> tempList = player.getPurchasedWeapons();
							tempList.add(godSword);
							player.setPurchasedWeapons(tempList);
						}
					}
				}
			} else if (gameState == 5) {
				if (ScreenGameOver.getMenuButton().contains(e.getPoint())) {
					stopGame();
					gameState = 1;
				}
			}
		}
	}

	/**
	 * KeyListen
	 * @author Pravinthan
	 * @date June 14 2017
	 * Class that handles keyboard input
	 */
	public class KeyListen implements KeyListener {
		
		/**
		 * keyPressed
		 * Method that performs actions initiated by keyboard
		 * Returns nothing
		 * @param KeyEvent e - Represents keyboard action
		 * @return void
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			// player 1 (W, S, A, D, SPACE)
			setFlagsForPlayer(e, 0, true);
			if (e.getKeyCode() == KeyEvent.VK_O && !playerList.get(0).isDead()) {
				// make sure other players' shop is not open before opening another
				if (playerList.size() == 1) { // if one player version of the game
					if (gameState == 3) {
						gameState = 4;
						playerList.get(0).setShopOpened(true);
					} else if (gameState == 4) {
						gameState = 3;
						playerList.get(0).setShopOpened(false);
					}
				} else if (playerList.size() == 2) { // if two player version of the game
					if (gameState == 3 && !playerList.get(1).isShopOpened()) {
						gameState = 4;
						playerList.get(0).setShopOpened(true);
					} else if (gameState == 4 && !playerList.get(1).isShopOpened()) {
						gameState = 3;
						playerList.get(0).setShopOpened(false);
					}
				}
			}

			// player 2 (UP, DOWN, LEFT, RIGHT, SLASH)
			if (playerList.size() == 2) { // if two player version of the game
				setFlagsForPlayer(e, 1, true); 
				if (e.getKeyCode() == KeyEvent.VK_P && !playerList.get(1).isDead()) {
					// make sure other players' shop is not open before opening another
					if (gameState == 3 && !playerList.get(0).isShopOpened()) {
						gameState = 4;
						playerList.get(1).setShopOpened(true);
					} else if (gameState == 4 && !playerList.get(0).isShopOpened()) {
						gameState = 3;
						playerList.get(1).setShopOpened(false);
					}
				}
			}
		}

		/**
		 * keyReleased
		 * Method that performs actions initiated by keyboard
		 * Returns nothing
		 * @param KeyEvent e - Represents keyboard action
		 * @return void
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			setFlagsForPlayer(e, 0, false); // player 1 (W, S, A, D, SPACE)

			if (playerList.size() == 2) { // if two player version of the game
				setFlagsForPlayer(e, 1, false); // player 2 (UP, DOWN, LEFT, RIGHT, SLASH)
			}
		}

		/**
		 * setFlagsForPlayer
		 * Method that helps change booleans for players
		 * Returns nothing
		 * @param e - Represents keyboard event
		 * @param playerListIndex - int holding number of players
		 * @param b - Boolean for changing value
		 * @return void
		 */
		private void setFlagsForPlayer(KeyEvent e, int playerListIndex, boolean b) {
			//if (!playerList.get(playerListIndex).isDead()) {
				//player 1
				if (playerListIndex == 0) {
					if (e.getKeyCode() == KeyEvent.VK_W) {
						playerList.get(playerListIndex).setUp(b);
					} else if (e.getKeyCode() == KeyEvent.VK_S) {
						playerList.get(playerListIndex).setDown(b);
					} else if (e.getKeyCode() == KeyEvent.VK_A) {
						playerList.get(playerListIndex).setLeft(b);
					} else if (e.getKeyCode() == KeyEvent.VK_D) {
						playerList.get(playerListIndex).setRight(b);
					} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						playerList.get(playerListIndex).setSpace(b);
					}
				} else if (playerListIndex == 1) { // player 2
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						playerList.get(playerListIndex).setUp(b);
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						playerList.get(playerListIndex).setDown(b);
					} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						playerList.get(playerListIndex).setLeft(b);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						playerList.get(playerListIndex).setRight(b);
					} else if (e.getKeyCode() == KeyEvent.VK_SLASH) {
						playerList.get(playerListIndex).setSpace(b);
					}
				}
			//}
		}// end of setFlagsForPlayer

		/**
		 * keyTyped
		 * Method that performs actions initiated by keyboard
		 * Returns nothing
		 * @param KeyEvent e - Represents keyboard action
		 * @return void
		 */
		@Override
		public void keyTyped(KeyEvent e) {} // end of keyTyped
	}// end of KeyListen
}// end of class