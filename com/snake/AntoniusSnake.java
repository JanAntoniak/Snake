//tu bedzie main i uruchamianie gry

class AntoniusSnake {
    public static void main(String[] args) {
    		if(args[0] == "server") {
    			Server server = new Server;
    			server.Start();
    		}
    		else if(args[0] = "client"){
    			Client client = new Client(args[1]);
    		}
    }
}