package domain.user;

public class Admin extends User{
    public Admin(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public void performRoleAction() {
        System.out.printf("You are now Admin. Hi %s!", super.getName());
    }

}
