package com.backend.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.backend.DataAcquisitionObjects.CrashDetectedNotificationDAO;
import com.backend.DataAcquisitionObjects.DeviceNotificationMethodDAO;
import com.backend.DataAcquisitionObjects.NotificationDAO;
import com.backend.Models.CrashDetected;
import com.backend.Models.CrashDetectedNotification;
import com.backend.Models.DeviceNotificationMethods;
import com.backend.Models.EmailNotification;
import com.backend.Models.Notification;
import com.backend.Models.NotificationStatus;
import com.backend.Models.TextNotification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationToNotificationProcessor {

    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.systemDefault());

    Executor threadExecutor;

    NotificationDAO notificationDAO;
    DeviceNotificationMethodDAO deviceNotificationMethodDAO;
    CrashDetectedNotificationDAO cdnDAO;

    SendGridUtil sendGridUtil;
    TwilioUtil twilioUtil;

    @Autowired
    public LocationToNotificationProcessor(
        NotificationDAO notificationDAO, 
        DeviceNotificationMethodDAO deviceNotificationMethodDAO,
        CrashDetectedNotificationDAO cdnDAO,
        SendGridUtil sendGridUtil,
        TwilioUtil twilioUtil) {
        this.threadExecutor = Executors.newFixedThreadPool(3);
        this.notificationDAO = notificationDAO;
        this.deviceNotificationMethodDAO = deviceNotificationMethodDAO;
        this.cdnDAO = cdnDAO;
        this.sendGridUtil = sendGridUtil;
        this.twilioUtil = twilioUtil;
    }

    public void sendNotificationsForCrash(CrashDetected crash) {
        List<DeviceNotificationMethods> notificationMethods = deviceNotificationMethodDAO.getDeviceNotificationMethodsByDeviceId(crash.getDeviceId());
        for(DeviceNotificationMethods notificationMethod: notificationMethods) {
            Notification notification = notificationDAO.getNotificationById(notificationMethod.getNotificationId());
            sendNotificationsForCrash(crash, notification);
        }
    }

    public void sendNotificationsForCrash(CrashDetected crash, Notification notificationMethod) {
        LocationToNotificationProcessorThread process = new LocationToNotificationProcessorThread(crash, notificationMethod);
        threadExecutor.execute(process);
    }

    class LocationToNotificationProcessorThread implements Runnable {

        CrashDetected crash;
        Notification notificationMethod;

        public LocationToNotificationProcessorThread(CrashDetected crash, Notification notificationMethod) {
            this.crash = crash;
            this.notificationMethod = notificationMethod;
        }

        @Override
        public void run() {
            CrashDetectedNotification cdn = cdnDAO.createCrashDetectedNotification(crash.getLocationId(), notificationMethod.getNotificationId());

            // Would have loved to used a sealed class here for class type switch statement from Java 17
            // Notification was a sealed class that permits TextNotification and EmailNotification
            // However, sealed classes cannot be used with hibernate at this time due to this
            // runtime error being thrown - NotificationHibernateProxy cannot inherit from sealed class Notification
            try {
                if (notificationMethod instanceof TextNotification) {
                    TextNotification textNotification = (TextNotification) notificationMethod;
                    twilioUtil.sendMessage(textNotification.getPhoneNumber(), generateNotificationMessage(crash));

                } else if (notificationMethod instanceof EmailNotification) {
                    EmailNotification emailNotification = (EmailNotification) notificationMethod;
                    sendGridUtil.sendEmail(emailNotification.getEmail(), "Crash Detected", generateNotificationMessage(crash));
                } else {
                    throw new IllegalArgumentException(
                        "Notification method provided {"+notificationMethod.toString()+"} must be of type " +
                        "EmailNotification or TextNotification"
                        );
                }
                cdnDAO.updateCrashDetectedNotificationStatus(crash.getLocationId(), notificationMethod.getNotificationId(), NotificationStatus.SENT);
            } catch (Exception e) {
                cdnDAO.updateCrashDetectedNotificationStatus(crash.getLocationId(), notificationMethod.getNotificationId(), NotificationStatus.FAILED);
                throw e;
            }
        }        

    }

    private static String generateNotificationMessage(CrashDetected crash) {
        return String.format(
            "Crash was detected for device (deviceId=%d) at %s. " +
            "Crash was located at position (%f, %f) (lat/lon in degrees)", 
            crash.getDeviceId(),
            LocationToNotificationProcessor.DATE_TIME_FORMATTER.format(crash.getLocationTime()),
            crash.getLocationLat(),
            crash.getLocationLon()
        );
    }
    
}
