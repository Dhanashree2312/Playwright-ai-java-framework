package ai;

import java.util.List;

public class TestCaseResponse {

    private List<TestCase> testCases;

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public static class TestCase {

        private String testCaseId;
        private String title;
        private String description;
        private String priority;
        private List<String> preconditions;
        private List<String> testSteps;
        private String expectedResult;

        public String getTestCaseId() {
            return testCaseId;
        }

        public void setTestCaseId(String testCaseId) {
            this.testCaseId = testCaseId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public List<String> getPreconditions() {
            return preconditions;
        }

        public void setPreconditions(List<String> preconditions) {
            this.preconditions = preconditions;
        }

        public List<String> getTestSteps() {
            return testSteps;
        }

        public void setTestSteps(List<String> testSteps) {
            this.testSteps = testSteps;
        }

        public String getExpectedResult() {
            return expectedResult;
        }

        public void setExpectedResult(String expectedResult) {
            this.expectedResult = expectedResult;
        }
    }
}