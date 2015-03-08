package CryptoIrcClient;

import java.util.Calendar;

/**
 *
 * @author Илья
 */
public class ClientLogger {
    public static void StartAuthLog(String login, String password){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        System.out.println("Запуск аутентификации.");
        System.out.println("Отправка XOR-зашифрованных логина, пароля, секретного слова.");
        System.out.println("Логин :  " + login);
        System.out.println("Пароль : " + password);
        System.out.println();
    }
    
    public static void AuthLog(int sessionId){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        System.out.println("Получен открытый ключ.");
        System.out.println("Отправка RSA-зашифрованных секретного слова и ключа для шифрования XOR.");
        System.out.println("ID сессии аутентификации :  " + sessionId);
        System.out.println();
    }
    
    public static void AuthSuccess(int sessionId){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        System.out.println("Аутентификация завершена успешно.");
        System.out.println("Получен ключ доступа.");
        System.out.println("ID сессии :  " + sessionId);
        System.out.println();
    }
    
    public static void AuthFail(){
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        System.out.println("Аутентификация закончилась неудачно.");
        System.out.println();
    }
}
