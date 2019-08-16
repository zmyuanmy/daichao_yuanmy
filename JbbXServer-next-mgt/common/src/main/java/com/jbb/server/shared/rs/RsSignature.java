package com.jbb.server.shared.rs;

public class RsSignature {
    private String signature;
    private long timestamp;
    private String nonce;
    
    public RsSignature(){
        
    }
    
    public RsSignature(String signature, long timestamp, String nonce) {
        super();
        this.signature = signature;
        this.timestamp = timestamp;
        this.nonce = nonce;
    }


    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

}
