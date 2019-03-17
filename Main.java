import java.util.*;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;
//import javax.activation.Datahandler;
import javax.mail.internet.MimeMessage;

//you need to pull in the following
//javax.mail
//javax.activation.datahandler

//MIME stands for multipurpose internet mail extension

/*
FetchMail - Looking at text/html. maybe we can use that to directly get the data and store?
Rich is looking to get a json file
Can expand FetchEmail to look for images as well
MailContent - need to think of how to store $ per item basis
MailContent - need to think about how to use fetchemail's checkcontent to store to store the data
 */

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

        //instantiate new fetchemail class object
        FetchEmail fetcher = new FetchEmail();

        fetcher.fetch(mail_properties, email, password, emailsToRead);

    }




}
