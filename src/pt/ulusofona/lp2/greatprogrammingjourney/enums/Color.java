package pt.ulusofona.lp2.greatprogrammingjourney.enums;

public enum Color {
    /* constants */
    PURPLE("Purple"),
    GREEN("Green"),
    BROWN("Brown"),
    BLUE("Blue");

    /* field */
    private final String displayName;

    /* getters */
    public String getDisplayName() {
        return displayName;
    }

    /* constructor */
    Color(String displayName) {
        this.displayName = displayName;
    }

    /* methods */
    public static Color fromString(String colorStr) {
        if (colorStr == null){
            return null;
        }
        for (Color color : Color.values()) {
            if (color.getDisplayName().equalsIgnoreCase(colorStr)) {
                return color;
            }
        }
        return null;
    }
}
