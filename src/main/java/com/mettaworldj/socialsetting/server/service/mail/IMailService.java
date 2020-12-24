package com.mettaworldj.socialsetting.server.service.mail;

import com.mettaworldj.socialsetting.server.dto.general.NotificationEmail;

public interface IMailService {
    void sendMail(NotificationEmail notificationEmail);
}
