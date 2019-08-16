package com.jbb.server.common.exception;

public class BossException extends BaseLogicalException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -696030283999211776L;

    public BossException() {
        super(BOSS_ERROR, "jbb.xjl.error.exception.boss.error", "zh");
    }

    public BossException(String message) {
        super(BOSS_ERROR, message, "zh");
    }

    public BossException(String messageId, String language) {
        super(BOSS_ERROR, messageId, language);
    }

    public BossException(String messageId, String language, String parameter0) {
        super(BOSS_ERROR, messageId, language, parameter0);
    }
}
