package tests;


import Util.*;
import base.BaseTest;
import pages.*;
import org.testng.Assert;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class YatraTest extends BaseTest {

    HomePage home;
    offerspage offers;
    String parent;

    @Test(priority = 1)
    public void openOffersPage() {

        home = new HomePage(driver);
        offers = new offerspage(driver);

        home.closePopup();
        parent = driver.getWindowHandle();
        home.clickOffers();
        offers.switchToOffersWindow(parent);
    }

    @Test(priority = 2, dependsOnMethods = "openOffersPage")
    public void validateOffersTitle() {

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(
                offers.getTitle(),
                "Domestic Flights Offers | Deals on Domestic Flight Booking | Yatra.com",
                "Title mismatch"
        );
        soft.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "openOffersPage")
    public void validateBannerText() {

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(
                offers.getBanner(),
                "Great Offers & Amazing Deals",
                "Banner mismatch"
        );
        soft.assertAll();
    }

    @Test(priority = 4, dependsOnMethods = "openOffersPage")
    public void validateExcelAndHolidays() throws Exception {

        YatraExcelValidation.validateOffersPage(driver);
        HolidayandScreenshot.captureAndListHolidays(driver, parent);
    }
}