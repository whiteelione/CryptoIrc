package cryptoIrcServer.Utility;

import java.util.Calendar;

/**
 *
 * @author Илья
 */
public class ServerLogger {    
    public static void StartAuthLog(int id){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        System.out.println("Начата аутентификация пользователя.");
        System.out.println("Пользователю выделен id и отправлен открытый ключ RSA.");
        System.out.println("ID :  " + id);
        System.out.println();
    }
    
    public static void EndAuth(int id, boolean logged, String login, String password){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        System.out.println("Завершение аутентификации пользователя с id " + id + ".");
        if(logged){
            System.out.println("Аутентификация завершена успешно.");
            System.out.println("Id     : " + id);
            System.out.println("Логин  : " + login);
            System.out.println("Пароль : " + password);
        }else{
            System.out.println("Аутентификация не удалась.");
        }
        System.out.println();
    }
}
