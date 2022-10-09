package com.twentyfour_seven.catvillage.security.refreshToken;

import com.twentyfour_seven.catvillage.audit.DateTable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "REFRESH_TOKEN")
@Entity
public class RefreshToken extends DateTable {

    @Id
    @Column(name = "RT_KEY", length = 700)
    private String key;

    @Column(name = "RT_VALUE", length = 700)
    private String value;

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
