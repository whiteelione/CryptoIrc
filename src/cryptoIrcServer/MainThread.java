package cryptoIrcServer;



public class MainThread {
    
    public static void main(String[] args) {
        Server server = new Server(4000, 100);
        server.Start();
    }
}
