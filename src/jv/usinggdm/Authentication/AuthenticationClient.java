package jv.usinggdm.Authentication;

import jv.usinggdm.Users.User;

import java.io.*;
import java.net.Socket;

public class AuthenticationClient {
        private static Socket socket;
         private static BufferedWriter bufferedWriter;
          private static BufferedReader bufferedReader;
            private static BufferedReader userInputReader;
                private static User user;

                public static void main(String[] args) {
                        try{
                            socket = new Socket("localhost", AuthenticationServer.getPort4454());
                             bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                             bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                             userInputReader = new BufferedReader(new InputStreamReader(System.in));
                             while (true) {
                                 String interactionMode = userInputReader.readLine();
                                 bufferedWriter.write(interactionMode + "\n");
                                 bufferedWriter.flush();
                                 switch (interactionMode.toLowerCase()) {
                                     case "exit" :
                                         user = null;
                                         userInputReader.close();
                                         bufferedReader.close();
                                         bufferedWriter.close();
                                         socket.close();
                                         return;
                                     case "sign in":
                                         String result = SignIn();
                                            if(result.equals("Success")){
                                                IfSuccess ifSuccess = new IfSuccess(user);
                                            }
                                         break;
                                     case "sign up":
                                         SignUp();
                                         break;
                                     default:
                                         System.out.println("Uncheckable command");
                                         break;
                                 }
                             }
                        } catch (IOException ex){
                            System.out.println("Port is locked");
                            ex.printStackTrace();
                        }
                }

                private static void SignUp() throws IOException{
                            String username = userInputReader.readLine();
                                String email = userInputReader.readLine();
                                    String password = userInputReader.readLine();
                            bufferedWriter.write(username+"\n");
                                bufferedWriter.flush();
                                    bufferedWriter.write(email+"\n");
                                        bufferedWriter.flush();
                                            bufferedWriter.write(password+"\n");
                                                bufferedWriter.flush();
                            String answer = bufferedReader.readLine();
                                System.out.println(answer);
                }
                private static String SignIn() throws IOException{
                        String login = userInputReader.readLine();
                        String password = userInputReader.readLine();
                            bufferedWriter.write(login+"\n");
                                bufferedWriter.flush();
                                    bufferedWriter.write(password+"\n");
                                        bufferedWriter.flush();
                                    String answer = bufferedReader.readLine();
                                    System.out.println(answer);
                                        if(answer.equals("Success")) {
                                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                                            try {
                                                user = (User) ois.readObject();
                                            } catch (ClassNotFoundException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                        return answer;
                }
}
