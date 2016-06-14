package no.bekk.aws;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;

public class S3 {

    public static void main(String[] args) throws IOException {
        AmazonS3 s3 = new AmazonS3Client();
        Region region = Region.getRegion(Regions.EU_CENTRAL_1);
        s3.setRegion(region);

        String bucketName = "min-bucket";
        String key = "minfil.txt";

        //Opprett en bucket
        if (!s3.doesBucketExist(bucketName)) {
            System.out.println("Oppretter bucket: " + bucketName);
            s3.createBucket(bucketName);
        }

        // Last opp en fil
        System.out.println("Laster opp fil: " + key);
        s3.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));

        // Hent ned filen og skriv ut innholdet
        System.out.println("Henter ned fil: " + key);
        S3Object myObject = s3.getObject(new GetObjectRequest(bucketName, key));
        S3ObjectInputStream objectContent = myObject.getObjectContent();
        String objectAsString = StreamUtils.copyToString(objectContent, Charset.forName("UTF-8"));
        System.out.println("Innhold i filen: \n" + objectAsString);

        // List alle filer i en bucket
        System.out.println("Følgende filer eksisterer i bucket: " + bucketName);
        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName));
        objectListing.getObjectSummaries().stream()
                .forEach(object -> System.out.println(object.getKey()));

        System.out.println("Sletter fil: " + key);
        s3.deleteObject(bucketName, key);

    }

    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }

}
