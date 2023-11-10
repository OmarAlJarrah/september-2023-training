
public class Anime {
    private final String name;
    private final float rate;

    public Anime(String name, float rate) {
        this.name = normalizeName(name);
        Anime.checkRate(rate);
        this.rate = rate;
    }

    public static void checkRate(float rate) {
        if (rate < 0 || rate > 10)
            throw new IllegalArgumentException("Rate must be a float number between 0 and 10");
    }

    public static String normalizeName(String name) {
        String lowerCase = name.toLowerCase();
        String standardName = lowerCase.replaceAll("[^a-zA-Z0-9]", "");
        return standardName;
    }

    public String getName() {
        return name;
    }

    public float getRate() {
        return rate;
    }

}
