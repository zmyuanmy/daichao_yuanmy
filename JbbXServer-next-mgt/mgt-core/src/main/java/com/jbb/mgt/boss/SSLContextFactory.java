/**
 * 
 */
package com.jbb.mgt.boss;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;


public class SSLContextFactory
{
    private static SSLContext ctx;
    private final static String PROTOCAL_NAME = "TLS";

    public static SSLContext getInstance(boolean chkCert)
    {
        if (ctx == null)
        {
            try
            {
                ctx = SSLContext.getInstance(PROTOCAL_NAME);
                if (chkCert)
                {
                    System.err.println("===");
                }
                else
                {
                    ctx.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new SecureRandom());
                }
            } catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            } catch (KeyManagementException e)
            {
                e.printStackTrace();
            }
            return ctx;
        }
        return ctx;
    }
}
