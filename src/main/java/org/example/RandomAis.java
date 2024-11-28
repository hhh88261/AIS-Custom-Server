package org.example;

import java.util.Random;

public class RandomAis {
    private static final Random random = new Random();


    public int generateRandomMessageType() {
        return 1 + random.nextInt(3);
    }

    public int generateRandomRepeatIndicator() {
        return 0 + random.nextInt(2);
    }

    public int generateRandomMMSI() {
        return 100000000 + random.nextInt(900000000);
    }

    public int generateRandomNavigationStatus() {
        return 0 + random.nextInt(7);
    }

    public double generateRandomTurnRate() {
        return - 128.0 + (random.nextDouble() * 128.0);
    }

    public double generateRandomSpeed() {
        return random.nextDouble() * 100;
    }

    public int generateRandomPositionAccuracy() {
        return random.nextInt(2);
    }

    public double generateRandomLatitude() {
        // 위도는 -90.0에서 +90.0 범위
        return random.nextDouble() * 90.0;  // -90 + [0, 180)
    }

    public double generateRandomLongitude() {
        // 경도는 -180.0에서 +180.0 범위
        return random.nextDouble() * 180.0; // -180 + [0, 360)
    }

    public double generateRandomCourse() {
        return random.nextDouble() * 359;
    }

    public int generateRandomTrueHeading() {
        return random.nextInt(360);
    }

    public int generateRandomTimeStamp() {
        return random.nextInt(100);
    }

    public int generateRandomManeuverIndicator() {
        return random.nextInt(4);
    }

    public int generateRandomSpare() {
        return random.nextInt(4);
    }

    public int generateRandomRaimFlag() {
        return random.nextInt(2);
    }

    public int generateRandomSyncState() {
        return random.nextInt(2);
    }

    public int generateRandomSlotTimeOut() {
        return random.nextInt(8); // Example: 0 to 7
    }

    public int generateRandomSlotOffset() {
        return random.nextInt(64); // Example: 0 to 63
    }

}
