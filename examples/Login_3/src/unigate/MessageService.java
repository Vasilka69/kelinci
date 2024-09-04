package src.unigate;

/**
 * Сервис отправки сообщений в сервис протоколирования
 */
public interface MessageService extends BaseMessageService{

//    void sendProtocolEvent (String eventType, String objectType, String objectId, String object, Integer rev);

    void sendUserProtocolEvent (String login, String eventCode, String remoteIp);

//    void sendProxyCacheClearByUserId (String userId);
//
//    void sendProxyCacheClearByRoleId (String roleId);
//
//    void sendProxyCacheClearByToken (String token);
}

