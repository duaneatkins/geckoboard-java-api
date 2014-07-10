package nl.pvanassen.geckoboard.api.widget;

import java.io.IOException;
import java.util.Arrays;

import nl.pvanassen.geckoboard.api.JsonTestHelper;
import nl.pvanassen.geckoboard.api.error.ValidationException;
import nl.pvanassen.geckoboard.api.json.bulletgraph.RAGColor;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.node.ArrayNode;
import org.junit.Assert;
import org.junit.Test;

public class BulletGraphTest {

    @Test
    public void testJson() throws JsonProcessingException, IOException {
        BulletGraph widget = new BulletGraph("1234", false);
        widget.setAxisPoints(Arrays.asList(new String[] { "1", "2", "3", "4", "8", "0" }));

        widget.setComparative("10");
        widget.setLabel("test-label");
        widget.setProjected(10, 100);
        widget.setSubLabel("sub-test-label");
        widget.setCurrent(1, 10);
        widget.addRange(0, 10, RAGColor.RED);
        widget.addRange(10, 20, RAGColor.AMBER);
        widget.addRange(20, 30, RAGColor.GREEN);
        widget.validate();

        JsonNode data = JsonTestHelper.getJsonFromWidget(widget);

        Assert.assertNotNull(data.get("data"));
        JsonNode node = data.get("data");
        Assert.assertNull(node.get("widgetKey"));

        JsonNode item = node.get("item");
        Assert.assertEquals("test-label", item.get("label").asText());
        Assert.assertEquals("sub-test-label", item.get("sublabel").asText());
        Assert.assertTrue(item.get("axis").get("point").isArray());
        ArrayNode points = (ArrayNode) item.get("axis").get("point");
        Assert.assertEquals(6, points.size());
        Assert.assertEquals(4, points.get(3).asInt());
        Assert.assertEquals(8, points.get(4).asInt());
        Assert.assertEquals(0, points.get(5).asInt());

        Assert.assertTrue(item.get("range").isArray());
        ArrayNode ranges = (ArrayNode) item.get("range");
        Assert.assertEquals(3, ranges.size());
        Assert.assertEquals("red", ranges.get(0).get("color").asText());
        Assert.assertEquals(0, ranges.get(0).get("start").asInt());
        Assert.assertEquals(10, ranges.get(0).get("end").asInt());

        Assert.assertEquals(1, item.get("measure").get("current").get("start").asInt());
        Assert.assertEquals(10, item.get("measure").get("current").get("end").asInt());
        Assert.assertEquals(10, item.get("measure").get("projected").get("start").asInt());
        Assert.assertEquals(100, item.get("measure").get("projected").get("end").asInt());
        Assert.assertEquals(10, item.get("comparative").get("point").asInt());
        Assert.assertEquals("horizontal", node.get("orientation").asText());

    }

    @Test(expected = ValidationException.class)
    public void testValidateNewObject() {
        new BulletGraph("1234", true).validate();
    }

    @Test(expected = ValidationException.class)
    public void testValidateLabel() {
        BulletGraph widget = new BulletGraph("1234", false);
        widget.setAxisPoints(Arrays.asList(new String[] { "1", "2", "3", "4", "8", "0" }));

        widget.setComparative("10");
        widget.setProjected(10, 100);
        widget.setSubLabel("sub-test-label");
        widget.setCurrent(1, 10);
        widget.addRange(0, 10, RAGColor.RED);
        widget.addRange(10, 20, RAGColor.AMBER);
        widget.addRange(20, 30, RAGColor.GREEN);
        widget.validate();
    }

    @Test(expected = ValidationException.class)
    public void testValidateComparative() {
        BulletGraph widget = new BulletGraph("1234", false);
        widget.setAxisPoints(Arrays.asList(new String[] { "1", "2", "3", "4", "8", "0" }));

        widget.setLabel("test-label");
        widget.setProjected(10, 100);
        widget.setSubLabel("sub-test-label");
        widget.setCurrent(1, 10);
        widget.addRange(0, 10, RAGColor.RED);
        widget.addRange(10, 20, RAGColor.AMBER);
        widget.addRange(20, 30, RAGColor.GREEN);
        widget.validate();
    }
}
