import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.conf.ConfigurationLoadException;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201708.StatementBuilder;
import com.google.api.ads.dfp.axis.v201708.AudienceSegment;
import com.google.api.ads.dfp.axis.v201708.AudienceSegmentPage;
import com.google.api.ads.dfp.axis.v201708.AudienceSegmentServiceInterface;
import com.google.api.ads.dfp.axis.v201708.AudienceSegmentType;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.api.client.auth.oauth2.Credential;

import java.rmi.RemoteException;

public class ListAudienceSegments {

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


        StatementBuilder builder = new StatementBuilder()
                .where("type = :type")
                .orderBy("id ASC")
                .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT)
                .withBindVariableValue("type", AudienceSegmentType.FIRST_PARTY.toString());

        AudienceSegmentPage segments = audienceSegmentService.getAudienceSegmentsByStatement(builder.toStatement());

        AudienceSegment fps = segments.getResults(450);

        System.out.println(fps.toString());
    }
}
