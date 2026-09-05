package tests;

import ai.TestCaseGenerator;
import ai.TestCaseResponse;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCaseGeneratorTest{

    private TestCaseGenerator testCaseGenerator;

    @BeforeClass
    public void setUp(){
        testCaseGenerator =
                new TestCaseGenerator();
    }

    @Test
    public void generateLoginTestCases() {

        String requirement = """
                The application should allow a registered user
                to log in using a valid username and password.
                If invalid credentials are entered, an appropriate
                error message should be displayed.
                """;

        TestCaseResponse response =
                testCaseGenerator.generateTestCases(
                        requirement
                );

        Assert.assertNotNull(
                response,
                "Test case response should not be null"
        );

        Assert.assertNotNull(
                response.getTestCases(),
                "Test cases should not be null"
        );

        Assert.assertFalse(
                response.getTestCases().isEmpty(),
                "At least one test case should be generated"
        );

        System.out.println(
                "\n========== AI GENERATED TEST CASES ==========\n"
        );

        for (TestCaseResponse.TestCase testCase :
                response.getTestCases()) {

            System.out.println(
                    "ID: "
                            + testCase.getTestCaseId()
            );

            System.out.println(
                    "Title: "
                            + testCase.getTitle()
            );

            System.out.println(
                    "Priority: "
                            + testCase.getPriority()
            );

            System.out.println(
                    "Description: "
                            + testCase.getDescription()
            );

            System.out.println(
                    "Preconditions: "
                            + testCase.getPreconditions()
            );

            System.out.println("Steps:");

            for (String step :
                    testCase.getTestSteps()) {

                System.out.println(
                        "  - " + step
                );
            }

            System.out.println(
                    "Expected Result: "
                            + testCase.getExpectedResult()
            );

            System.out.println(
                    "---------------------------------------------"
            );
        }
    }

    @AfterClass
    public void tearDown() {

        if (testCaseGenerator != null) {
            testCaseGenerator.close();
        }
    }
}
