package nl.pvanassen.geckoboard.api.json.common;

/**
 * Text int value item holder for generic use
 *
 * @author Paul van Assen
 */
public class TextValueItem {

    private final String text;

    private final int value;

    public TextValueItem(String text, int value) {
        super();
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }
}