package grupoalan.backendgalan.model.request;

public class SessionInfo {
    private String currentUser;
    private String currentAccountType;

    public SessionInfo() {
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentAccountType() {
        return currentAccountType;
    }

    public void setCurrentAccountType(String currentAccountType) {
        this.currentAccountType = currentAccountType;
    }
}
