package pt.ulusofona.lp2.greatprogrammingjourney.enums;

public enum PlayerStatus {
    /* constants */
    IN_GAME("Em Jogo"),
    DEFEATED("Derrotado");

    /* field */
    private final String displayName;

    /* getters */
    public String getDisplayName() {
        return displayName;
    }

    /* constructor */
    PlayerStatus(String displayName) {
        this.displayName = displayName;
    }
}
