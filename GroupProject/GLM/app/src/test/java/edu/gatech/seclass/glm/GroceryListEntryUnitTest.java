package edu.gatech.seclass.glm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by kaidizhang on 10/13/16.
 */

public class GroceryListEntryUnitTest {
    private GroceryListEntry entry;
    private Item item;

    @Before
    public void setup() {
        ItemType type = new ItemType("fruit");
        item = new Item("apple", type);
        entry = new GroceryListEntry(item, 1.0, "lb");
    }

    @After
    public void tearDown() {
        entry = null;
    }

    @Test
    public void testGetQuantity() {
        assertEquals(1.0, entry.getQuantity(), 0);
    }

    @Test
    public void testGetUnit() {
        assertEquals("lb", entry.getUnit());
    }

    @Test
    public void testGetChecked() {
        assertFalse(entry.getChecked());
    }

    @Test
    public void testGetItem() {
        assertEquals(item, entry.getItem());
    }

    @Test
    public void testCheckItem() {
        entry.checkItem();
        assertTrue(entry.getChecked());
    }

    @Test
    public void testUnCheckItem() {
        entry.unCheckItem();
        assertFalse(entry.getChecked());
    }

    @Test
    public void testChangeQuantity1() {
        entry.changeQuantity(3);
        assertEquals(3.0, entry.getQuantity(), 0);
    }

    @Test
    public void testChangeUnit1() {
        entry.changeUnit("bags");
        assertEquals("bags", entry.getUnit());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testChangeQuantity2() {
        entry.changeQuantity(0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testChangeUnit2() {
        entry.changeUnit("");
    }
}
