package socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public void service() {
        new Thread() {

            @Override
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(8000);
                    System.out.println(ss.getLocalPort());
                    System.out.println("服务器启动成功!");
                    while(true) {
                        //死循环,一直accept,接受客户端的连接请求
                        Socket socket = ss.accept();
                        System.out.println("客户端连接成功!");
                        HuaWuThread t = new HuaWuThread(socket);
                        t.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            };
        }.start();
    }

    class HuaWuThread extends Thread{
        Socket socket;
        public HuaWuThread(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                String line;
                while((line = in.readLine()) != null) {
                    System.out.println("客户端发来的数据:" + line);
                    int j = line.indexOf("!");
                    String newString;
                    newString = line.substring(0,j);
                    int count = Integer.parseInt(newString);
                    out.println(count);
                    out.flush();//把数据刷出去
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Server s = new Server();
        s.service();
    }
}
