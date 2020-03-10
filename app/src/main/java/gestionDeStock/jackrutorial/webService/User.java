package gestionDeStock.jackrutorial.webService;

public class User
{

    private String username;
    private String password;


    //Setters and getters

    @Override
    public String toString() {
        return
                "user=" + username + ", "
                + "password=" + password + ", ";
    }
}