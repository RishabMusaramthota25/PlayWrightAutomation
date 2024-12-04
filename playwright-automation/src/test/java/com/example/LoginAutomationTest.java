package com.example;

import com.microsoft.playwright.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import java.io.InputStream;

public class LoginAutomationTest {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterAll
    static void tearDown() {
        browser.close();
        playwright.close();
    }

    @Test
    @DisplayName("Test Login Functionality")
    void testLogin() {
        LoginCredentials credentials = loadLoginCredentials();

        if (credentials != null) {
            page.navigate("https://animated-gingersnap-8cf7f2.netlify.app/");

            page.fill("input[id='username']", credentials.getEmail());
            page.fill("input[id='password']", credentials.getPassword());

            page.click("button[type='submit']");

            page.waitForSelector("text=Web Application");

            Assertions.assertTrue(
                page.isVisible("text=Web Application"),
                "Login failed. 'Web Application' text is not visible"
            );

            System.out.println("Login successful for user: " + credentials.getEmail());
        } else {
            Assertions.fail("Failed to load login credentials.");
        }
    }

    @Test
    @DisplayName("Test Case 2: Verify 'Fix navigation bug' in 'To Do'")
    void verifyFixNavigationBugInToDo() {
        page.click("text=Web Application");

        Assertions.assertTrue(
            page.isVisible("text=Fix navigation bug"),
            "'Fix navigation bug' task is not found in 'To Do' column."
        );

        Assertions.assertTrue(
            page.isVisible("text=Bug"),
            "'Bug' tag is not found for 'Fix navigation bug' task."
        );

        System.out.println("Test Passed: 'Fix navigation bug' task is in 'To Do' with correct tags.");
    }

    @Test
    @DisplayName("Test Case 3: Verify 'Design system updates' in 'In Progress'")
    void verifyDesignSystemUpdatesInProgress() {
        page.click("text=Web Application");

        Assertions.assertTrue(
            page.isVisible("text=Design system updates"),
            "'Design system updates' task is not found in 'In Progress' column."
        );

        Assertions.assertTrue(
            page.isVisible("text=Design"),
            "'Design' tag is not found for 'Design system updates' task."
        );

        System.out.println("Test Passed: 'Design system updates' task is in 'In Progress' with correct tags.");
    }

    @Test
    @DisplayName("Test Case 4: Verify 'Push notification system' in 'To Do' (Mobile Application)")
    void verifyPushNotificationSystemInToDo() {
        page.click("text=Mobile Application");

        Assertions.assertTrue(
            page.isVisible("text=Push notification system"),
            "'Push notification system' task is not found in 'To Do' column."
        );

        Assertions.assertTrue(
            page.isVisible("text=Feature"),
            "'Feature' tag is not found for 'Push notification system' task."
        );

        System.out.println("Test Passed: 'Push notification system' task is in 'To Do' with correct tags.");
    }

    @Test
    @DisplayName("Test Case 5: Verify 'Offline mode' in 'In Progress' (Mobile Application)")
    void verifyOfflineModeInProgress() {
        page.click("text=Mobile Application");

        Assertions.assertTrue(
            page.isVisible("text=Offline mode"),
            "'Offline mode' task is not found in 'In Progress' column."
        );

        Assertions.assertTrue(
            page.isVisible("text=Feature"),
            "'Feature' tag is not found for 'Offline mode' task."
        );

        Assertions.assertTrue(
            page.isVisible("text=High Priority"),
            "'High Priority' tag is not found for 'Offline mode' task."
        );

        System.out.println("Test Passed: 'Offline mode' task is in 'In Progress' with correct tags.");
    }

    @Test
    @DisplayName("Test Case 6: Verify 'App icon design' in 'Done' (Mobile Application)")
    void verifyAppIconDesignInDone() {
        page.click("text=Mobile Application");

        Assertions.assertTrue(
            page.isVisible("text=App icon design"),
            "'App icon design' task is not found in 'Done' column."
        );

        Assertions.assertTrue(
            page.isVisible("text=Design"),
            "'Design' tag is not found for 'App icon design' task."
        );

        System.out.println("Test Passed: 'App icon design' task is in 'Done' with correct tags.");
    }

    /**
     * Loads login credentials from a JSON file in resources
     *
     * @return LoginCredentials object with email and password
     */
    private static LoginCredentials loadLoginCredentials() {
        try {
            // Load the JSON file from resources
            InputStream is = LoginAutomationTest.class.getClassLoader().getResourceAsStream("testData.json");

            if (is == null) {
                throw new IllegalArgumentException("Resource file not found: loginData.json");
            }

            // Deserialize JSON into LoginCredentials object
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(is, LoginCredentials.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
