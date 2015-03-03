package cryptoIrcServer;



import java.io.Console;

public class ServerConsole {
    
    private final Server mServer;
    private boolean serverLaunched;
    
    public ServerConsole(Server server){
        mServer = server;
        serverLaunched = true;
    }
    
    public void Start(){
        println("");
        println("Запущен цикл обработки сообщений.");
        println("Введите /help для вывода справки.");
        Console console = System.console();
        while(serverLaunched){
            String command = console.readLine();
            ProcessCommand(command);
        }
    }
    
    private void ProcessCommand(String command){
        switch(command){
            case "/help":
                break;
            case "/stop":
                StopServer();
                System.exit(0);
                break;
            default:
                println("Команда не найдена! Введите /help для вывода справки.");
                break;
        }
    }
    
    public void StopServer(){
        println("Выключение сервера.");
        serverLaunched = false;
        mServer.Stop();
    }
    
    public void println(Object obj){
        System.out.println("\t" + obj);
    }
    
    public void print(Object obj){
        try { Thread.sleep(250); } catch (InterruptedException ex) { }
        System.out.println(obj);
    }
}
