package com.simplesmtp;
import java.util.Scanner;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SimpleSMTP {                                                   // Class SimpleSMTP has 2 final String values username(or Email), and Password 
final String username;
final String password;
public SimpleSMTP(String username,String password){                         // Constructor for the class is being created inorder to store (or accept) the email and password
    this.username=username;
    this.password=password;
}
public void sendEmail(String Sub,String msg,String sender,String to) {      // Method sendEmail defines the properties of the connection to the JAVA MAIL API and sends the email
    Properties props = new Properties();                                    // properties method is used to set up the connection properties
    props.put("mail.smtp.auth", "true");                                    // here we set up the SMTP AUTH value to true to enable AUTHENTICATION        
    props.put("mail.smtp.starttls.enable", "true");                         // here setting the AUTHENTICATION method to TLS and enabling it 
    props.put("mail.smtp.host", "smtp.gmail.com");                          // here we set up the SMTP host as Gmail , you can change it to your desired ones(example for Yahoo use:  smtp.mail.yahoo.com)
    props.put("mail.smtp.port", "587");                                     // here we set up the port number as 587( NOTE donot use use port 26 as in many systems it is blocked by os )
    Session session = Session.getInstance(props,                            // here we start a new session and pass the system properties we jusst defined above 
      new javax.mail.Authenticator() {                                      // initialisng the Authenticator and Authenticating to google 
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);          // passing the given email and password to ensure login is successful before every message attempt
        }
      });

    try {                                                                   //sending the message in an try catch block as it enables smooth functioning an helps catching errors
        Message message = new MimeMessage(session);                         // making message a MIME  message (MIME stands for: Multipurpose Internet Mail Extensions)
        message.setFrom(new InternetAddress(sender));                       // as MIME is a Mail messaging extension so it will have a from and to address
        message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(to));                                     //parses the email address of the Recipient (to address)
        message.setSubject(Sub);                                            // subject of the message goes here
        message.setText(msg);                                               // The body of the messages goes here
        Transport.send(message);                                            //literally calls the Transport layer and sends the message which was defined above
        System.out.println("Your email was sent successfully.....Have a nice day!!!");
    } catch (MessagingException e) {
        throw new RuntimeException(e);                                      // if error are thrown it will be shown here
    	}
	}
	public static void main(String[] args) {                                // Here basically everything is self explainatory so please read carefully
        Scanner sc = new Scanner(System.in);
        final String Email;
        final String Pass;
        final String To;
        final String Body;
        final String Subject;
        System.out.println("Please enter the Recipeient's Name :");
        To = sc.next();
        System.out.println("please enter the Subject of your email :");
        Subject = sc.next();
        System.out.println("Please enter the Body of you message( NOTE! this will be the message you want to send) :");
        Body = sc.next();
        System.out.println("Please enter your email Address :");
        Email = sc.next();
        System.out.println("Please enter your password (Press Enter when done) :");
        Pass = sc.next();
        sc.close();
		SimpleSMTP s = new SimpleSMTP(Email, Pass);
		s.sendEmail(Subject, Body, Email, To);
	}
}