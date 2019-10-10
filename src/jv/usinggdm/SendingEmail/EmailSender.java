package jv.usinggdm.SendingEmail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender extends Thread{

                private String senderName;
                    private String senderPassword;
                        private String recipient;
                            private String subject;
                                private String body;
                            private final String HOST = "smtp.gmail.com";
                            private Session session;
                            private MimeMessage message;


      public EmailSender(String senderName, String senderPassword){
            this.senderName = senderName;
            this.senderPassword = senderPassword;
        Properties props = System.getProperties();
         props.put("mail.smtp.starttls.enable", "true");
          props.put("mail.smtp.host", HOST);
           props.put("mail.smtp.user", senderName);
            props.put("mail.smtp.password", senderPassword);
             props.put("mail.smtp.port", "587");
              props.put("mail.smtp.auth", "true");

         session = Session.getDefaultInstance(props);
         message = new MimeMessage(session);
      }

            private void send() throws AddressException, MessagingException{
                message.setFrom(new InternetAddress(senderName));;

                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

                message.setSubject(subject);
                message.setText(body);

                Transport transport = session.getTransport("smtp");
                transport.connect(HOST, senderName, senderPassword);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            }

            @Override
            public void run(){
                    try{
                        send();
                    } catch (AddressException ex){
                        System.out.println("Address exception");
                    } catch (MessagingException ex){
                        System.out.println("Messaging exception");
                    }
            }

            // Getters and Setters
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setBody(String body) {
        this.body = body;
    }
            // Getters and Setters

}
