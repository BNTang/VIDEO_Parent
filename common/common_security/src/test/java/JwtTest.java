/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */

import top.it6666.common_security.security.TokenManager;

/**
 * @author BNTang
 * @version 1.0
 * @project video_parent
 * @description
 * @since Created in 2021/5/4 004 0:39
 **/
public class JwtTest {
    public static void main(String[] args) {
        TokenManager tokenManager = new TokenManager();
        String token = tokenManager.createToken("BNTang");

        token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSSkzJzcxT0lFKrShQsjI0MzIwtDQxMjapBQD4MPjkIAAAAA.7mH6Gv6YHEMg2CVFBoZ3DzSPM3dTehtiFvS_mTFH3fRpv05hg0UYZlhvVCC7eLyfhlz8b8cKiHZJXL__YCl3wg";

        String userFromToken = tokenManager.getUserFromToken(token);
        System.out.println(userFromToken);
    }
}
