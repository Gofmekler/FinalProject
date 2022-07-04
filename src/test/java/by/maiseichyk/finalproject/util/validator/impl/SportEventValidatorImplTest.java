package by.maiseichyk.finalproject.util.validator.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SportEventValidatorImplTest {

    @Test
    public void testCheckTeamName() {
        SportEventValidatorImpl sportEventValidator = SportEventValidatorImpl.getInstance();
        String name = "Янки";
        Assert.assertTrue(sportEventValidator.checkTeamName(name));
    }

    @Test
    public void testCheckTeamRatio() {
        SportEventValidatorImpl sportEventValidator = SportEventValidatorImpl.getInstance();
        String ratio = "3.4";
        Assert.assertTrue(sportEventValidator.checkTeamRatio(ratio));
    }

    @Test
    public void testCheckEventDate() {
        SportEventValidatorImpl sportEventValidator = SportEventValidatorImpl.getInstance();
        String date = "2020-12-12";
        Assert.assertTrue(sportEventValidator.checkEventDate(date));
    }

    @Test
    public void testCheckEventType() {
        SportEventValidatorImpl sportEventValidator = SportEventValidatorImpl.getInstance();
        String eventType2 = "FOOTBALL";
        Assert.assertTrue(sportEventValidator.checkEventType(eventType2));
    }
}