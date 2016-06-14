package no.bekk.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;

import java.io.IOException;


public class SES {

    static final String FROM = "test@example.com";  // Epost-adressen må verifiseres i SES-console
    static final String TO = "test@example.com"; // Epost-adressen må verifiseres i SES-console
    static final String BODY = "This email was sent through Amazon SES by using the AWS SDK for Java.";
    static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";

    public static void main(String[] args) throws IOException {

        AWSCredentials credentials;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(credentials);
        Region region = Region.getRegion(Regions.EU_WEST_1);
        client.setRegion(region);

        Destination destination = new Destination().withToAddresses(TO);

        Content subject = new Content().withData(SUBJECT);
        Content textBody = new Content().withData(BODY);
        Body body = new Body().withText(textBody);

        Message message = new Message().withSubject(subject).withBody(body);

        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);

        client.sendEmail(request);
        System.out.println("Email sent!");
    }
}