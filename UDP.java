import java.net.*;
import java.io.*;

public class main {

    public static void main(String[] args) {

        int port = 7727;

        DatagramSocket dsock = null;
        String file_loc = "C:\\Users\\user\\Desktop\\and_data\\";
        DatagramPacket receivePacket, sendPacket;
        File file;
        BufferedWriter bufferedWriter;


        try{

            System.out.println("접속 대기상태입니다.");

            dsock = new DatagramSocket(port);
            String msg;

            byte[] buffer = new byte[1024]; // 30도 ok
            receivePacket = new DatagramPacket(buffer, buffer.length);

            dsock.receive(receivePacket);
            msg = new String(receivePacket.getData(), 0, receivePacket.getLength());

            System.out.println(msg);

            file = new File(file_loc + msg + ".txt");
            bufferedWriter = new BufferedWriter(new FileWriter(file));

            sendPacket = new DatagramPacket(receivePacket.getData(),
                    receivePacket.getData().length, receivePacket.getAddress(), receivePacket.getPort());

            dsock.send(sendPacket);

            while(true){

                dsock.receive(receivePacket);
                msg = new String(receivePacket.getData(), 0, receivePacket.getLength());

                if(msg.equals("quit")) {
                    bufferedWriter.close();
                    break;
                }
                bufferedWriter.write(msg);
            }
            System.out.println("UDPEchoServer를 종료합니다.");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(dsock != null)
                dsock.close();
        }
    }
}
