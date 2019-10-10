package jv.usinggdm.Authentication;

import jv.usinggdm.Users.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationServer {
        private static final int PORT_4454 = 4454;
        private static int usersCount = 0;
         private static ServerSocket serverSocket;
          private static Socket socket;
           private static BufferedWriter bufferedWriter;
            private static BufferedReader bufferedReader;
             private static BufferedWriter fileOutputStream;
              private static BufferedReader fileInputStream;
                    private static User user;

            public static void main(String[] args) {
                    try{
                        serverSocket = new ServerSocket(PORT_4454);
                          socket = serverSocket.accept();
                            System.out.println("Socket accepted, port: "+ PORT_4454);
                        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                         bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                                while (true) {
                                    fileOutputStream = new BufferedWriter(new FileWriter("D:\\Java\\Projs\\BookAccounting\\Database\\Users.txt", true));
                                    fileInputStream = new BufferedReader(new FileReader("D:\\Java\\Projs\\BookAccounting\\Database\\Users.txt"));
                                    String interactionModel = bufferedReader.readLine();
                                    switch (interactionModel.toLowerCase()) {
                                        case "exit" :
                                            user = null;
                                            fileOutputStream.close();
                                            fileInputStream.close();
                                            bufferedReader.close();
                                            bufferedWriter.close();
                                            serverSocket.close();
                                            socket.close();
                                            return;
                                        case "sign in":
                                            System.out.println("Sign in");
                                            SignIn();
                                            break;
                                        case "sign up":
                                            System.out.println("Sign up");
                                            SignUp();
                                            break;
                                        default:
                                            System.out.println("Uncheckable command");
                                            break;
                                    }
                                }
                    } catch (IOException ex){
                        System.out.println("Port locked");
                        ex.printStackTrace();
                    }

            }

            private static void SignUp() throws  IOException{
                String username = bufferedReader.readLine();
                 String email = bufferedReader.readLine();
                  String password = bufferedReader.readLine();
                    String passwordtoWrite = toCode(password);
                            int accessModificator = 0;

                if(userExists(username, email)){
                    bufferedWriter.write("User exists\n");
                        bufferedWriter.flush();
                } else if (!password.matches("\\w++")){
                    bufferedWriter.write("Unacceptable input\n");
                        bufferedWriter.flush();
                } else if(username.length()<=20&&username.matches("\\w++")&&email.matches("[a-zA-Z0-9[-. _]]+?@gmail\\.com")) {
                    password = null;
                    fileOutputStream.write(usersCount + "[u:" + username + "]" + "[m:" + email + "]" + "[p:" + passwordtoWrite + "]" + "[a:" + accessModificator + "]" + "\r\n");
                    fileOutputStream.flush();
                    bufferedWriter.write("Account has been created\n");
                        bufferedWriter.flush();
                } else {
                    bufferedWriter.write("Unacceptable input\n");
                        bufferedWriter.flush();
                }
            }
            private static void SignIn() throws IOException{
                    String login = bufferedReader.readLine();
                     String password = bufferedReader.readLine();
                     String answer = "Incorrect login\n";
                        String email = "";

                     Pattern usernamePattern = Pattern.compile("\\[u:\\w++]");
                     Pattern emailPattern = Pattern.compile("\\[m:[a-zA-Z0-9[-. _]]++@gmail\\.com]");
                     Pattern passwordPattern = Pattern.compile("\\[p:\\w++]");

                        String line = fileInputStream.readLine();
                            while(line!=null){

                                Matcher foundUsername = usernamePattern.matcher(line);
                                Matcher foundEmail = emailPattern.matcher(line);
                                Matcher foundPassword = passwordPattern.matcher(line);

                                while(foundUsername.find())
                                        if(line.substring(foundUsername.start()+3, foundUsername.end()-1).toLowerCase().equals(login.toLowerCase())) {
                                            answer = "Incorrect password\n";
                                            while (foundPassword.find()) {
                                                System.out.println(line.substring(foundPassword.start() + 3, foundPassword.end() - 1));
                                                if (line.substring(foundPassword.start() + 3, foundPassword.end() - 1).equals(toCode(password))) {
                                                       while (foundEmail.find())
                                                    email = line.substring(foundEmail.start() + 3, foundEmail.end() - 1);
                                                    user = new User(line.toCharArray()[0]);
                                                    user.setUsername(login);
                                                    user.setEmail(email);
                                                    user.setAccessModifier(Integer.valueOf(String.valueOf(line.toCharArray()[line.length()-2])));
                                                    answer = "Success\n";
                                                    bufferedWriter.write(answer);
                                                    bufferedWriter.flush();
                                                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                                    oos.writeObject(getUser());
                                                    oos.flush();
                                                    return;
                                                }
                                            }
                                        }
                                line = fileInputStream.readLine();
                            }
                                bufferedWriter.write(answer);
                                    bufferedWriter.flush();
            }

            private static boolean userExists(String username, String email) throws IOException {
                            String line = fileInputStream.readLine();
                            Pattern usernamePattern = Pattern.compile("\\[u:\\w+?]");
                            Pattern emailPattern = Pattern.compile("\\[m:[a-zA-Z0-9[-. _]]+?@gmail\\.com]");

                                while(line!=null){

                                    Matcher foundUsername = usernamePattern.matcher(line);
                                    Matcher foundEmail = emailPattern.matcher(line);

                                    while(foundUsername.find()) {
                                        if (line.substring(foundUsername.start()+3, foundUsername.end()-1).toLowerCase().equals(username.toLowerCase()))
                                            return true;
                                    }

                                        while(foundEmail.find()) {
                                            if (line.substring(foundEmail.start()+3, foundEmail.end()-1).equals(email))
                                                return true;
                                        }

                                    line = fileInputStream.readLine();
                                }
                        return false;
            }
            private static String toCode(String source){
                        StringBuilder code = new StringBuilder();
                            for(char c: source.toCharArray()){
                                int i=17;
                                code.append((byte)c+i);
                                i = (int) Math.pow(i,2);
                                code.append("_");
                            }
                        return code.toString();
                }

                // Getters
                public static int getPort4454() {
                    return PORT_4454;
                }
                private static User getUser(){return user;}
                // Getters
}
