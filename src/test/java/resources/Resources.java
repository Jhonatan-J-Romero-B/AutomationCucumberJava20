package resources;

import java.util.Arrays;
import java.util.List;

public class Resources {

    public static class Environments{
        public static final String GOOGLE = "https://www.google.com/";
        public static final String I_AM_LITTLE_TESTER = "https://www.imalittletester.com/";
        public static final String MERCURY_TOURS = "https://demo.guru99.com/test/newtours/";
        public static final String DEMO_BLAZE = "https://www.demoblaze.com/index.html";
        public static final String DEMO_QA = "https://demoqa.com/";
    }

    public static class Drivers{
        public static final List<String> CHROME = Arrays.asList("webdriver.chrome.driver","./src/test/resources/drivers/chromedriver.exe","webdriver.chrome.silentOutput","true");
    }

    public static class ChromeOptions{
        public static final String REMOTE_ALLOW_ORIGINS = "--remote-allow-origins=*";
    }

    public static class ChromePreferences{
        public static final String PROFILE_DEFAULT = "profile.default_content_settings.popups";
        public static final String DOWNLOAD_DEFAULT = "download.default_directory";
        public static final String DATA_INPUT_EXCEL = ".src/test/resources/testData/TestPlan_1.xlsx";
    }

    public static class ChromeExperimentalOptions{
        public static final String PREFS = "prefs";
    }

    public static class Attributes{
        public static final String PLACEHOLDER = "placeholder";
        public static final String VALUE = "value";
        public static final boolean TRUE = true;
        public static final boolean FALSE = false;
    }
}