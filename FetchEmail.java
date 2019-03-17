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


public class FetchEmail {
    /*___________________________________FUNCTION___________________________________*/
    //constructor for fetchemail class
    public void fetch(Properties mail_properties, String email, String password, int emailsToRead) {
        try {
            //create a session for store
            Session mail_session = Session.getDefaultInstance(mail_properties, null);
            Store mail_store = mail_session.getStore("imaps");

            System.out.println("Attempting to connect to email");
            //try connecting to google imaps
            mail_store.connect(email, password);
            System.out.println("Successfully connected.");

            //get the folder you want to read from (this is inbox here)
            Folder inbox = mail_store.getFolder("inbox");
            //open the inbox folder so we can read the messages
            inbox.open(Folder.READ_WRITE);

            //gets all the messages
            Message[] messages = inbox.getMessages();

            System.out.println("Here are the following " + messages.length + " message(s).");
            //store and read the email information


            for (int i=0; i < messages.length; i++) {
                Message nextMessage = messages[i];
                MailContent message_contents = new MailContent();

                System.out.println("________________________________________");
                System.out.println("Email Number: " + (i+1));

//                checkContentType(nextMessage); //implement on this
                //stores the message's information
                message_contents.storeContent(nextMessage);

                //print out the email information
                System.out.println("Date: " + message_contents.getDateReceived());
                System.out.println("Subject: " + message_contents.getSubject());


            }


        }//end of try

        catch (Exception error) {
            System.out.println("ERROR, MAN");
            error.printStackTrace();
        }//end of catch
    }

    /*___________________________________FUNCTION___________________________________*/
    //checks the type of content of each message
    private void checkContentType(Part p) throws Exception {
//        System.out.print(p.getContentType());

        //if the content of the email is only text
        if ( p.isMimeType("text/plain")) {
//            System.out.println("text/plain content...");
//            System.out.println((String) p.getContent());
        }

        //this pulls the html information. do we want this?  maybe to pull?
//        else if (p.isMimeType("text/html")) {
//            System.out.println("text/html content...");
//            System.out.println((String) p.getContent());
//        }

        //if the content of the email is multipart (we iterate through all the parts)
        else if ( p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
//            System.out.println("multipart content with parts " + mp.getCount() + "...");
            for (int i=0; i < mp.getCount(); i++) {
//                System.out.println("checking the " + i + "th body party");
                checkContentType(mp.getBodyPart(i));
            }
        }

        //if the content of the email is nested message
        else if (p.isMimeType("message/rfc822")) {
//            System.out.println("nested message content...");
            checkContentType((Part) p.getContent());
        }
    }

}
