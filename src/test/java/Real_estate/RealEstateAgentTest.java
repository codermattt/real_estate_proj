package Real_estate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RealEstateAgentTest {

    @Test
    void testRealEstateObjectWorks() {
        Real_Estate estate = new Real_Estate("Budapest", 250000, 100, 4, Real_Estate.Genre.CONDOMINIUM);

        assertNotNull(estate.getCity());
        assertTrue(estate.getPrice() > 0);
        assertTrue(estate.getSqm() > 0);
        assertTrue(estate.getNumberOfRooms() > 0);
        assertNotNull(estate.getGenre());

        assertTrue(estate.getTotalPrice() > 0);
        assertTrue(estate.averageSqmPerRoom() > 0);

        System.out.println("RealEstate object works correctly!");
    }

    @Test
    void testPanelInheritanceWorks() {
        Panel panel = new Panel("Debrecen", 120000, 35, 2, Real_Estate.Genre.CONDOMINIUM, 0, true);

        assertEquals("Debrecen", panel.getCity());
        assertTrue(panel.getTotalPrice() > 0);

        assertEquals(0, panel.getFloor());
        assertTrue(panel.IsInsulated());
        assertTrue(panel.roomPrice() > 0);

        System.out.println("Panel object works correctly!");
    }
}
