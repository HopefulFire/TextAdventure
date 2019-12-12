package NPCHandler;

import java.util.EventObject;

public class NPCFindTargetEvent extends EventObject {
    NPC source;

    /**
     * Constructs a NPCFindTargetEvent
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public NPCFindTargetEvent(NPC source) {
        super(source);
        this.source = source;
    }

    /**
     * Gets this Events NPC
     *
     * @return the NPC
     */
    @Override
    public NPC getSource() {
        return source;
    }
}