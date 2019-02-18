import java.util.*;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;
//import javax.activation.Datahandler;
import javax.mail.*;

//you need to pull in the following
//javax.mail
//javax.activation.datahandler


public class Main {

    private static String email = "jtsodapop@gmail.com";
    private static String password = "CoreyRich";
    static int emailsToRead = 5;

    public static void main(String[] args) {
        //generation of properties for mail
        Properties mail_properties = new Properties();
        mail_properties.put("mail.store.protocol", "imaps"); //secure imap
        mail_properties.put("mail.imaps.host", "imap.gmail.com"); //gmail
        mail_properties.put("mail.imaps.port", "993"); //secure port for imaps


        try {
//            //create a session for store
            Session mail_session = Session.getDefaultInstance(mail_properties, null);
            Store mail_store = mail_session.getStore("imaps");

            System.out.println("Attempting to connect to email");
            //try connecting to google imaps
            mail_store.connect(email, password);
            System.out.println("Successfully connected.");

            //get the folder you want to read from
            Folder inbox = mail_store.getFolder("inbox");
            //open the inbox folder so we can read the messages
            inbox.open(Folder.READ_WRITE);

            //number of emails
            int numEmails = inbox.getMessageCount();
            System.out.println("You have " + numEmails + " total emails");

            emailsToRead = numEmails;


//            for (int i = emailsToRead; i > 0; i--) {
//                System.out.println(inbox.getMessage(numEmails).getSubject());
//            System.out.println(inbox.getMessage(1).getSubject());
//           }

            System.out.println(inbox.getMessage(1));
        }



        catch (Exception error) {
            System.out.println("ERROR, MAN");
            error.printStackTrace();
        }



//        System.out.println(mail_properties);
    }



}
