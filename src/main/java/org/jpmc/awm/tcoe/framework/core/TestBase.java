//package org.jpmc.awm.tcoe.framework.core;
//
//import io.cucumber.java.*;
//import org.jpmc.awm.tcoe.framework.utils.ArchiveUtils;
//import org.jpmc.awm.tcoe.framework.utils.BannerPrinter;
//import org.jpmc.awm.tcoe.framework.utils.FileUtilsHelper;
//import org.jpmc.awm.tcoe.framework.utils.ScreenshotUtils;
//
//import java.io.File;
//
//public class TestBase {
//    private static boolean isLogCleanupDone = false;
//
//    @Before
//    public void setup() {
//        TestContext.setup();
//    }
//
//    @After
//    public void teardown() {
//        TestContext.teardown();
//    }
//
//    @BeforeAll
//    public static void globalSetup() {
//        if (!isLogCleanupDone) {
//            FileUtilsHelper.deleteLogsFolder();
//            isLogCleanupDone = true;
//        }
//        TestContext.setup();
//    }
//
//    @AfterAll
//    public static void afterAllTests() {
//        ArchiveUtils.archiveTestRunArtifacts();
//    }
//}
