import java.util.*;
//import javax.mail.*;
import java.util.Properties;
//import javax.activation.Datahandler;
import javax.mail.internet.MimeMessage;
import javax.mail.Multipart;
import javax.mail.Store;
import javax.mail.Session;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Address;


public class MailContent {
    //would like to turn it into a json object/file to give to rich
    String subject;
    //    String recipient; // do we need recipient?
    String[] sender;
    Date date_received;


    /*___________________________________FUNCTION___________________________________*/
    public void storeContent(Message message) {
        Address[] addresses;


        try {
            //get the subject
            subject = message.getSubject();

            //get the senders
//            if ((addresses = message.getFrom()) != null) {
//                System.out.println(addresses.length);
//                for (int i=0; i< addresses.length; i++) {
//                    sender[i] = addresses[i].toString();
//                }
//            }

            //get the received date
            date_received = message.getReceivedDate();
        }

        catch (Exception error) {
            System.out.println("Error getting the contents of the message.");
            error.printStackTrace();
        }
    }

    /*___________________________________FUNCTION___________________________________*/
    public String getSubject() {
//        System.out.println(subject);
        return subject;
    }

    public Date getDateReceived() {
//        System.out.println(date_received);
        return date_received;
    }

    //need to think of how to store $ per item basis
}
