package ai;

public class OllamaRequest {

    private String model;
    private String system;
    private String prompt;
    private boolean stream;

    public OllamaRequest(String model, String system, String prompt, boolean stream) {
        this.model = model;
        this.system = system;
        this.prompt = prompt;
        this.stream = stream;
    }

    public String getModel() {
        return model;
    }

    public String getSystem() {
        return system;
    }

    public String getPrompt() {
        return prompt;
    }

    public boolean isStream() {
        return stream;
    }
}