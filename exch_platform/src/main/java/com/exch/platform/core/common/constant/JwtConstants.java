
package com.exch.platform.core.common.constant;

/**
 * jwt相关配置
 *
 */
public interface JwtConstants {

    String AUTH_HEADER = "Authorization";

    String SECRET = "defaultSecret";

    Long EXPIRATION = 604800L;

    String AUTH_PATH = "/gunsApi/auth";

}
