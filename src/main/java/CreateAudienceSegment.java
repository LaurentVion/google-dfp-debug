import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.conf.ConfigurationLoadException;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201708.StatementBuilder;
import com.google.api.ads.dfp.axis.v201708.*;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.api.client.auth.oauth2.Credential;

import java.rmi.RemoteException;
import java.util.Random;

public class CreateAudienceSegment {
    public static void main(String[] args) throws ConfigurationLoadException, ValidationException, OAuthException, RemoteException {

        Credential oAuth2Credential = new OfflineCredentials.Builder()
                .forApi(OfflineCredentials.Api.DFP)
                .fromFile()
                .build()
                .generateCredential();

        DfpSession session = new DfpSession.Builder()
                .fromFile()
                .withOAuth2Credential(oAuth2Credential)
                .build();

        DfpServices dfpServices = new DfpServices();

        AudienceSegmentServiceInterface audienceSegmentService = dfpServices.get(session, AudienceSegmentServiceInterface.class);

        NonRuleBasedFirstPartyAudienceSegment audienceSegment = new NonRuleBasedFirstPartyAudienceSegment();
        audienceSegment.setName("Sports enthusiasts audience segment #" + new Random().nextInt(Integer.MAX_VALUE));
        audienceSegment.setDescription("Sports enthusiasts between the ages of 20 and 30.");
        audienceSegment.setMembershipExpirationDays(88);

        System.out.println(audienceSegment.toString());

        AudienceSegment[] audienceSegments = audienceSegmentService.createAudienceSegments(
                new FirstPartyAudienceSegment[] { audienceSegment });

        for (AudienceSegment createdAudienceSegment : audienceSegments) {
            System.out.printf(
                    "An audience segment with ID %d, name '%s', and type '%s' was created.%n",
                    createdAudienceSegment.getId(), createdAudienceSegment.getName(),
                    createdAudienceSegment.getType());
        }
    }
}
