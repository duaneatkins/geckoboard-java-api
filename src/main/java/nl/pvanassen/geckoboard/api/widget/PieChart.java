package nl.pvanassen.geckoboard.api.widget;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import nl.pvanassen.geckoboard.api.Push;
import nl.pvanassen.geckoboard.api.error.ValidationException;
import nl.pvanassen.geckoboard.api.json.common.LabelValueColorItem;

import com.google.gson.annotations.SerializedName;

/**
 * Pie chart widget
 *
 * @author Paul van Assen
 */
public class PieChart extends Push {

    @SerializedName("item")
    private final List<LabelValueColorItem> items = new LinkedList<LabelValueColorItem>();

    public PieChart(String widgetKey) {
        super(widgetKey);
    }

    public void addItem(String label, String value, Color color) {
        items.add(new LabelValueColorItem(label, value, color));
    }

    @Override
    protected void validate() throws ValidationException {
        if (items.size() == 0) {
            throw new ValidationException("item", "At least one item expected");
        }
    }
}
