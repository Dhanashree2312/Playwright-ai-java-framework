package ai;

import java.util.List;

public class AIAnalysisResponse {

    private boolean valid;
    private List<String> issues;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getIssues() {
        return issues;
    }

    public void setIssues(List<String> issues) {
        this.issues = issues;
    }
}