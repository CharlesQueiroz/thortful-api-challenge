package api.challenge.thortful.domain.model;

public enum GenderType {
    MALE, FEMALE, NONE;

    public static GenderType fromString(String gender) {
        if (gender == null) {
            return null;
        }
        return switch (gender.toLowerCase()) {
            case "male" -> MALE;
            case "female" -> FEMALE;
            default -> NONE;
        };
    }
}
