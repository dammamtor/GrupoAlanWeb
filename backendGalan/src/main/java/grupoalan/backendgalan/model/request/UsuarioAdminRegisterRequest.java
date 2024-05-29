package grupoalan.backendgalan.model.request;

public class UsuarioAdminRegisterRequest {
    private String email;
    private String username;
    private String password;
    private AccountType accountType;

    public enum AccountType {
        ADMIN
    }

    public UsuarioAdminRegisterRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
