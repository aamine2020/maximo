package gestionDeStock.jackrutorial.webService;

public class ApiUtils {


    public static UserService getUserService(String url){
        return RetrofitClient.getClient(url).create(UserService.class);
    }
}