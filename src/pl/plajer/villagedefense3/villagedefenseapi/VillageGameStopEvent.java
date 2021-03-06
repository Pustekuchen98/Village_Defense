package pl.plajer.villagedefense3.villagedefenseapi;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.plajer.villagedefense3.arena.Arena;

/**
 * @author Plajer
 * @since 3.0.0-ALPHA build 13
 * <p>
 * Called when arena is stopped (game has ended).
 */
public class VillageGameStopEvent extends VillageEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Arena arena;

    public VillageGameStopEvent(Arena arena) {
        super(arena);
        this.arena = arena;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Arena getArena() {
        return arena;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

}
