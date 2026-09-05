package ai;

public class OllamaResponse{
    private Message message;

    public Message getMessage(){
        return message;
    }

    public void setMessage(Message message){
        this.message=message;
    }
    public static class Message{
        private String role;
        private String content;

        public String getRole(){
            return role;
        }

        public void setRole(String role){
            this.role=role;
        }
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
