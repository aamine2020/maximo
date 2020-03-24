package controller;

public class ApiUtils {


    public static UserService getUserService(String url){
        return RetrofitClient.getClient(url).create(UserService.class);
    }

    public static QueryService getQueryService(String url){
        return RetrofitClient.getClient(url).create(QueryService.class);
    }
}