package pl.polsl.lab.worldcupstats.tests;

import org.junit.*;
import static org.junit.Assert.*;

import pl.polsl.lab.worldcupstats.models.*;

/**
 * This class test WolrdCupStatsModel public methods
 *
 * @author Paweł Szwajnoch
 * @version 1.1
 */
public class WorldCupStatsModelTest {

    private static WorldCupStatsModel testModel;

    @BeforeClass
    public static void setup() {
        testModel = new WorldCupStatsModel();
    }

    /**
     * Infills groups with random teams to provide properly execution of tests
     */
    @Before
    public void fillGroups() {
        testModel.resetGroupIndex();

        for (int i = 0; i < testModel.getAmountOfGroups(); i++) {
            try {
                testModel.setActualGroupIndex(i);
                testModel.addTeamToActualGroup("A");
                testModel.addTeamToActualGroup("B");
                testModel.addTeamToActualGroup("C");
                testModel.addTeamToActualGroup("D");
            } catch (TextContainsForbiddenCharsException | TooManyTeamsException | WrongGroupIndexException ex) {
            }
        }
    }

    /**
     * Tests if SetActualGroupIndex method throws exception when argument is
     * wrong or if method doesn't when argument is correct
     */
    @Test
    public void testSetActualGroupIndex() {
        try {
            testModel.setActualGroupIndex(2);
            testModel.setActualGroupIndex(0);
        } catch (WrongGroupIndexException ex) {
            fail("An exception shouldn't be thrown when the index is correct");
        }

        try {
            testModel.setActualGroupIndex(-10);
            fail("An exception should be thrown when the index is non-positive");
        } catch (WrongGroupIndexException ex) {
        }

        try {
            testModel.setActualGroupIndex(300);
            fail("An exception should be thrown when the index is greater than amount of groups");
        } catch (WrongGroupIndexException ex) {
        }

    }

    /**
     * Tests if addTeamToActualGroup method throws exceptions when argument is
     * wrong or if method doesn't when argument is correct
     */
    @Test
    public void testAddTeamToActualGroup() {
        try {
            testModel.addTeamToActualGroup("Poland");
            testModel.addTeamToActualGroup("P");
            testModel.addTeamToActualGroup("qwerty");
            testModel.addTeamToActualGroup("Germany");
            fail("An exception shouldn't be thrown when the amount of teams is < 4");
            fail("An exception shouldn't be thrown when the team name is correct");
        } catch (TooManyTeamsException | TextContainsForbiddenCharsException ex) {
        }

        try {
            testModel.addTeamToActualGroup("");
            testModel.addTeamToActualGroup("123");
            testModel.addTeamToActualGroup(":,;!");
            fail("An exception should be thrown when the team name is empty or has other chars than letters");
        } catch (TooManyTeamsException | TextContainsForbiddenCharsException ex) {
        }

        try {
            testModel.addTeamToActualGroup("Czech Republic");
            fail("An exception should be thrown when the amount of teams is >= 4");
        } catch (TooManyTeamsException | TextContainsForbiddenCharsException ex) {
        }
    }

    /**
     * Tests if resetGroupIndex method correctly resets (sets to 0)
     * actualGroupIndex variable (private field in WorldCupStatsModel class)
     */
    @Test
    public void testResetGroupIndex() {
        testModel.resetGroupIndex();
        assertEquals("Group index set to zero", testModel.getActualGroupIndex(), 0);
    }

    /**
     * Tests if setActualGroupResultsConfirmed method correctly sets actual
     * group IsConfirmed variable to true (private field in Group class)
     */
    @Test
    public void testSetActualGroupResultsConfirmed() {
        testModel.setActualGroupResultsConfirmed();
        assertTrue("Actual group results set to confirmed", testModel.isActualGroupConfirmed());
    }

    /**
     * Tests if setActualGroupResults method throws exception when argument is
     * wrong or if method doesn't when argument is correct. Invokes
     * prepareTestSetActualGroupResults method
     */
    @Test
    public void testSetActualGroupResults() {

        testModel.setEachGroupMatches();

        try {
            String[] strings = {"1:0", "1:1", "8:1", "1:2", "5:0", "10:9"};
            testModel.setActualGroupResults(strings);

        } catch (TextContainsForbiddenCharsException ex) {
            fail("An exception shouldn't be thrown when the strings are correct (have form of NUMBER:NUMBER");
        }

        try {
            String[] strings = {"A:0", "1:1", "8:1", "1:2", "5:0", "7:9"};
            testModel.setActualGroupResults(strings);
            fail("An exception should be thrown when any string have other form than NUMBER:NUMBER");
        } catch (TextContainsForbiddenCharsException ex) {
        }

        try {
            String[] strings = {"", "1:1", "", "1:2", "", "7:9"};
            testModel.setActualGroupResults(strings);
            fail("An exception should be thrown when any string is empty");
        } catch (TextContainsForbiddenCharsException ex) {
        }

        try {
            String[] strings = {"11", "1:1", "43", "1:2", "00", "7:9"};
            testModel.setActualGroupResults(strings);
            fail("An exception should be thrown when any string doesn't consists of ':' ");
        } catch (TextContainsForbiddenCharsException ex) {
        }
    }

    /**
     * Tests if after filling all groups with 4 teams, WorldCupStatsModel method
     * areGroupsFull returns correct value
     */
    @Test
    public void testAreGroupsFull() {
        assertTrue("Groups filled with 4 teams", testModel.areGroupsFull());
    }

    /**
     * Tests if settings matches between teams works properly
     */
    @Test
    public void testSetEachGroupMatches() {

        testModel.setEachGroupMatches();

        for (int i = 0; i < testModel.getAmountOfGroups(); i++) {
            String[] strings = testModel.getActualGroupMatches();
            for (String s : strings) {
                assertNotNull("All matches should be propely set", s);
            }
        }
    }

}
