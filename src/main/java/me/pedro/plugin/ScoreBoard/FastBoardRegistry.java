package me.pedro.plugin.ScoreBoard;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.pedro.plugin.Core;

/**
 * a class that represents fast board registries.
 *
 * <pre>{@code
 *   final class ExamplePlugin extends JavaPlugin {
 *
 *     private final FastBoardRegistry registry = new FastBoardRegistry(this)
 *     // private final FastBoardRegistry registry = new FastBoardRegistry(this, // Delay 0L)
 *     // private final FastBoardRegistry registry = new FastBoardRegistry(this, // Delay 0L, // Interval 20L)
 *       .setOnRegister(board -> {
 *         // Runs when a player registers to the fast board registry.
 *         // This calls when the player joins to the server.
 *         // If you have static lines and title you can set here.
 *         board.updateTitle(ChatColor.RED + "FastBoard");
 *       })
 *       .setOnPeriod(board -> {
 *         // Runs with a period that you set before.
 *         board.updateLines(
 *           "",
 *           "Players: " + this.getServer().getOnlinePlayers().size(),
 *           "",
 *           "Kills: " + board.getPlayer().getStatistic(Statistic.PLAYER_KILLS),
 *           ""
 *         );
 *       })
 *       .setOnUnregister(board -> {
 *         // Runs when a player unregisters from the registry.
 *         // This calls when the player quits from the game.
 *       });
 *
 *     @Override
 *     public void onEnable() {
 *       registry.initiate(this);
 *     }
 *   }
 * }</pre>
 */
public final class FastBoardRegistry {

  /**
   * the plugin.
   */
  private final Core plugin;

  /**
   * the entrypoint.
   */
  private final Entrypoint entrypoint = new Entrypoint(this);

  /**
   * the boards.
   */
  private final Map<UUID, FastBoard> boards = new HashMap<>();

  /**
   * the period delay.
   */
  private final long periodDelay;

  /**
   * the period interval.
   */
  private final long periodInterval;

  /**
   * the task.
   */
  private final Task task = new Task(this);

  /**
   * the on register.
   */
  private Consumer<FastBoard> onRegister = fastBoard -> {
  };

  /**
   * the on unregister.
   */
  private Consumer<FastBoard> onUnregister = fastBoard -> {
  };

  /**
   * the on period.
   */
  private Consumer<FastBoard> onPeriod = fastBoard -> {
  };

  /**
   * ctor.
   *
   * @param plugin         the plugin.
   * @param periodDelay    the period delay.
   * @param periodInterval the period interval.
   */
  public FastBoardRegistry(final Core plugin, final long periodDelay, final long periodInterval) {
    this.plugin = Objects.requireNonNull(plugin, "plugin");
    this.periodDelay = periodDelay;
    this.periodInterval = periodInterval;
  }

  /**
   * ctor.
   *
   * @param plugin      the plugin.
   * @param periodDelay the period delay.
   */
  public FastBoardRegistry(final Core plugin, final long periodDelay) {
    this(plugin, periodDelay, 20L);
  }

  /**
   * ctor.
   *
   * @param plugin the plugin.
   */
  public FastBoardRegistry(final Core plugin) {
    this(plugin, 0L);
  }

  /**
   * obtains the on register.
   *
   * @return on register.
   */
  public Consumer<FastBoard> getOnRegister() {
    return this.onRegister;
  }

  /**
   * sets the on register.
   *
   * @param onRegister the on register to set.
   * @return {@code this} for the builder chain.
   */
  public FastBoardRegistry setOnRegister(final Consumer<FastBoard> onRegister) {
    this.onRegister = Objects.requireNonNull(onRegister, "on register");
    return this;
  }

  /**
   * obtains the on unregister.
   *
   * @return on unregister.
   */
  public Consumer<FastBoard> getOnUnregister() {
    return this.onUnregister;
  }

  /**
   * sets the on unregister.
   *
   * @param onUnregister the on unregister to set.
   * @return {@code this} for the builder chain.
   */
  public FastBoardRegistry setOnUnregister(final Consumer<FastBoard> onUnregister) {
    this.onUnregister = Objects.requireNonNull(onUnregister, "on unregister");
    return this;
  }

  /**
   * obtains the on period.
   *
   * @return on period.
   */
  public Consumer<FastBoard> getOnPeriod() {
    return this.onPeriod;
  }

  /**
   * sets the on period.
   *
   * @param onPeriod the on period to set.
   * @return {@code this} for the builder chain.
   */
  public FastBoardRegistry setOnPeriod(final Consumer<FastBoard> onPeriod) {
    this.onPeriod = Objects.requireNonNull(onPeriod, "on period");
    return this;
  }

  /**
   * initiates the registry.
   */
  public void initiate() {
    this.plugin.getServer().getPluginManager().registerEvents(this.entrypoint, this.plugin);
    this.task.runTaskTimer(this.plugin, this.periodDelay, this.periodInterval);
  }

  /**
   * obtains the boards.
   *
   * @return boards.
   */
  public Map<UUID, FastBoard> getBoards() {
    return Collections.unmodifiableMap(this.boards);
  }

  /**
   * registers the user.
   *
   * @param player the player to register.
   */
  public void registerUser(final Player player) {
    final FastBoard board = new FastBoard(player);
    this.onRegister.accept(board);
    this.boards.put(player.getUniqueId(), board);
  }

  /**
   * unregisters the user.
   *
   * @param player the player to unregister.
   */
  public void unregisterUser(final Player player) {
    final FastBoard removed = this.boards.remove(player.getUniqueId());
    this.onUnregister.accept(removed);
    if (removed != null) {
      removed.delete();
    }
  }

  /**
   * a class that represents tasks for fast board registries.
   */
  private static final class Task extends BukkitRunnable {

    /**
     * the registry.
     */
    private final FastBoardRegistry registry;

    /**
     * ctor.
     *
     * @param registry the registry.
     */
    private Task(final FastBoardRegistry registry) {
      this.registry = Objects.requireNonNull(registry, "registry");
    }

    @Override
    public void run() {
      for (final FastBoard board : this.registry.getBoards().values()) {
        this.registry.getOnPeriod().accept(board);
      }
    }
  }

  /**
   * a class that represents entrypoint of the server to register/unregister the fast board for each player
   */
  private static final class Entrypoint implements Listener {

    /**
     * the registry.
     */
    private final FastBoardRegistry registry;

    /**
     * ctor.
     *
     * @param registry the registry.
     */
    private Entrypoint(final FastBoardRegistry registry) {
      this.registry = Objects.requireNonNull(registry, "registry");
    }

    /**
     * runs when a player joins to the server.
     *
     * @param event the event to run.
     */
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
      registry.registerUser(event.getPlayer());
    }

    /**
     * runs when a player quits from the server.
     *
     * @param event the event to run.
     */
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
      registry.unregisterUser(event.getPlayer());
    }
  }
}