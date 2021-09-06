package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public final class UserDetails {

    private UUID id;
    private Status status;
    private Role role;
    private String email;
    private String phone;
    private List<Privilege> authorities;
    private ForceChangePassword forceChangePassword;
    private AuthType authType;
    private List<String> socials;

    public enum Role {
        INVESTOR,
        TRADER,
        PROJECT,
        SYSTEM,
    }

    public enum Status {
        UNKNOWN,
        UNVERIFIED,
        ACTIVE,
        DISABLED,
        FROZEN
    }

    public enum ForceChangePassword {
        UNNECESSARY,
        REQUIRED,
        MANDATORY
    }

    public enum AuthType {
        COOKIE,
        API_KEY,
        JWT,
        API_KEY_MM
    }

    public enum Privilege {
        MANAGE_ACCOUNT,
        VIEW_ACCOUNT,
        VIEW_MARKET_DATA,
        PLACE_ORDER,
        CANCEL_ORDER,
        DEPOSIT_WALLET,
        WITHDRAW_WALLET,
        DEPOSIT_SPOT,
        WITHDRAW_SPOT,
        APPLY_KYC,
        REQUEST_KYC,
        UPLOAD_FILE,
        GRANT_API,
        REVOKE_API,
        VIEW_API,
        VIEW_QUOTAS,
        MANAGE_SESSIONS,
        VIEW_TRANSACTIONS,
        EMAIL_TRANSFER,
        PHONE_TRANSFER,
        ID_TRANSFER,
        VIEW_TRANSFERS,
        CROWDSALE_VIEW,
        CROWDSALE_BUY,
        BYPASS_TWOFA,
        EXCHANGE_ASSET,
        INVITE_REFERRAL,
        REQUEST_2FA,
        MANAGE_CROWDFUNDING,
        VIEW_PARTNER_REFERRALS,
    }
}
