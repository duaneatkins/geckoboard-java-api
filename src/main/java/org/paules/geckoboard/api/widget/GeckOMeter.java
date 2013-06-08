package org.paules.geckoboard.api.widget;

import org.paules.geckoboard.api.Push;
import org.paules.geckoboard.api.error.ValidationException;
import org.paules.geckoboard.api.json.common.GraphType;
import org.paules.geckoboard.api.json.common.TextStrValueItem;

import com.google.gson.annotations.SerializedName;

public class GeckOMeter extends Push {
    @SerializedName("type")
    private final GraphType type;

    @SerializedName("item")
    private String          current;

    private TextStrValueItem       min;

    private TextStrValueItem       max;

    public GeckOMeter( String widgetKey, GraphType type ) {
        super( widgetKey );
        this.type = type;
    }
    
    @Override
    protected void validate() throws ValidationException {
        if (current == null || current.isEmpty()) {
            throw new ValidationException( "current", "Must be set");
        }
        if (min == null ) {
            throw new ValidationException( "min", "Must be set");
        }
        if (max == null ) {
            throw new ValidationException( "max", "Must be set");
        }
        if (type == null ) {
            throw new ValidationException( "type", "Must be set");
        }
    }

    public void setCurrent( String current ) {
        this.current = current;
    }

    public void setMax( String label, String value ) {
        max = new TextStrValueItem( label, value );
    }

    public void setMin( String label, String value ) {
        min = new TextStrValueItem( label, value );
    }
}
