package NPCHandler;

import java.util.EventListener;

/**
 * The interface for FindTargetListeners
 *
 * Date Last Modified: 12/14/2019
 * @author Daniel Masker, Ben Hodsdon, Emma Smith, Joseph Teahen
 *
 * CS1131, fall 2019
 * Lab Section 2
 */
public interface NPCFindTargetListener extends EventListener {
    void handle(NPCFindTargetEvent event);
}
