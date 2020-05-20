package site.springbike.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import site.springbike.database.DatabaseManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class EmailSender {
    private static String API;

    public static void init() throws IOException {
        ClassLoader classLoader = EmailSender.class.getClassLoader();
        URL resource = classLoader.getResource("credentials.txt");
        if (resource == null) {
            throw new RuntimeException("File credentials.txt not found");
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(resource.getFile()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith("API")) {
                API = line.split("=", 2)[1].strip();
            }
        }
        System.out.println(API);
    }

    public static boolean sendEmail(String toAddress, String emailSubject, String emailContent) {
        if (API == null) {
            return false;
        }
        Email from = new Email("info@springbike.site");
        String subject = emailSubject;
        Email to = new Email(toAddress);
        Content content = new Content("text/plain", emailContent);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(API);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
}
