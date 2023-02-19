package socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
public class Client {
    public static void main(String[] args) {
        try {
            // 连接指定的服务器,同时指定服务器的IP和端口号
            Socket socket = new Socket("127.0.0.1",8000);
            long count = 0;
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            long byteCount = 0;
            long startTime = System.nanoTime();
            while(true) {
                count++;
                String input = count +"!" + "<?xml version=\"1.0\"?>" + "<RTDATA><TABLE NAME=\"YD_YARDLANE\">"
                        + "<ROWDATA OPTYPE=\"UPDATE\" YARDLANENO=\"01A\" ISLOCK=\"N\" LASTUPDATETIME=\"2020/3/31 8:45:38\"/>"
                        + "<ROWDATA OPTYPE=\"UPDATE\" YARDLANENO=\"111\" ISLOCK=\"N\" LASTUPDATETIME=\"2020/3/31 8:45:38\"/>"
                        + "</TABLE></RTDATA>" + "<?xml version=\"1.0\"?>" + "<RTDATA><TABLE NAME=\"YD_YARDLANE\">"
                        + "<ROWDATA OPTYPE=\"UPDATE\" YARDLANENO=\"01A\" ISLOCK=\"N\" LASTUPDATETIME=\"2020/3/31 8:45:38\"/>"
                        + "<ROWDATA OPTYPE=\"UPDATE\" YARDLANENO=\"111\" ISLOCK=\"N\" LASTUPDATETIME=\"2020/3/31 8:45:38\"/>"
                        + "</TABLE></RTDATA>";
                byteCount = byteCount + input.length();
                out.println(input);//向服务端发送指定数据
                out.flush();//把数据刷出去
                String line = in.readLine();//读取回声数据
                int count_back = Integer.parseInt(line);
                if (count_back == count){
                    // System.out.println("客户端数据第" + count + "次发送" + ",数据传输正常，无丢包");
                }else{
                    System.out.println("客户端数据第" + count + "次发送" + ",数据传输异常，发生丢包");
                }
                // 计算传输速率
                if (byteCount > 1048576 * 10){
                    // System.out.println(byteCount);
                    long endTime = System.nanoTime();
                    long timeCost = endTime - startTime;
                    // System.out.println(timeCost);
                    double speed = (double)byteCount / timeCost * 1000 * 1000 * 1000 / 1048576;
                    System.out.println("Transmission rate:" + speed);
                    byteCount = 0;
                    count = 0;
                    startTime = System.nanoTime();
                }
//                延时
//                try
//                {
//                    Thread.sleep(1000);//单位：毫秒
//                } catch (Exception e) {
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
