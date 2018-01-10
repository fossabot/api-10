package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dongfg.project.api.graphql.type.SecretKey;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author dongfg
 * @date 17-11-9
 */
@Component
@Slf4j
public class TotpResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private static final String OTP_AUTH_FORMAT = "otpauth://totp/%s:@%s?secret=%s";
    private static final GoogleAuthenticator GOOGLE_AUTHENTICATOR = new GoogleAuthenticator();

    public SecretKey createSecretKey(String site, String username) {
        String secret = GOOGLE_AUTHENTICATOR.createCredentials().getKey();
        String otpAuthString = String.format(OTP_AUTH_FORMAT, site, username, secret);
        return SecretKey.builder()
                .secret(secret)
                .qrCodeUrl(String.format("http://qr.liantu.com/api.php?w=400&text=%s", otpAuthString)).build();
    }

    public boolean validateCode(String secretKey, int code) {
        return GOOGLE_AUTHENTICATOR.authorize(secretKey, code);
    }
}
