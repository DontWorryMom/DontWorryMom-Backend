package com.backend.util;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.backend.DataAcquisitionObjects.DeviceNotificationMethodDAO;
import com.backend.DataAcquisitionObjects.NotificationDAO;
import com.backend.Models.CrashDetected;
import com.backend.Models.DeviceNotificationMethods;
import com.backend.Models.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationToNotificationProcessor {

    Executor threadExecutor;
    NotificationDAO notificationDAO;
    DeviceNotificationMethodDAO deviceNotificationMethodDAO;

    @Autowired
    public LocationToNotificationProcessor(
        NotificationDAO notificationDAO, 
        DeviceNotificationMethodDAO deviceNotificationMethodDAO) {
        this.threadExecutor = Executors.newFixedThreadPool(3);
        this.notificationDAO = notificationDAO;
        this.deviceNotificationMethodDAO = deviceNotificationMethodDAO;
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
            System.out.println(crash.toString());
            System.out.println(notificationMethod.toString());
        }



    }
    
}
