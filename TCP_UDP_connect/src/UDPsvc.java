

import java.io.*; 
import java.net.*; 
  
class UDPServer { 
  public static void main(String args[]) throws Exception 
    { 
  
      System.out.println("UDP Server Socket Created");
      DatagramSocket serverSocket = new DatagramSocket(9876); 
  
      byte[] receiveData = new byte[1024]; 
      byte[] sendData  = new byte[1024]; 
  
      while(true) 
        { 
  
          DatagramPacket receivePacket = 
              new DatagramPacket(receiveData, receiveData.length); 
             
          serverSocket.receive(receivePacket); 

	      String sentence = new String(receivePacket.getData()); 
	  
	      System.out.println(sentence+"/n");
  
          InetAddress IPAddress = receivePacket.getAddress(); 
  
          int port = receivePacket.getPort(); 
  
          String capitalizedSentence = sentence.toUpperCase(); 

          sendData = capitalizedSentence.getBytes(); 
  
          DatagramPacket sendPacket = 
             new DatagramPacket(sendData, sendData.length, IPAddress,port); 
  
          serverSocket.send(sendPacket); 
        } 
    } 
}  
