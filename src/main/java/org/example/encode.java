package org.example;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


//1.초기 데이터 필드들을 2진수로 변환
//2.필드의 2진수를 한 문자열로 합친 후 6비트씩 나누기
//3.나눠진 6비트를 하나씩 CHAR로 변환
//4.체크섬 계산 : "!" 이후의 모든 문자열 XOR 연산

public class encode {
    private static final Map<String, Character> BIT_TO_CHAR = new HashMap<>();
    private static final Map<Character, String> CHAR_TO_BIT = new HashMap<>();



    static {
        // 테이블을 초기화
        String[] bits = {
                "000000", "000001", "000010", "000011", "000100", "000101", "000110", "000111",
                "001000", "001001", "001010", "001011", "001100", "001101", "001110", "001111",
                "010000", "010001", "010010", "010011", "010100", "010101", "010110", "010111",
                "011000", "011001", "011010", "011011", "011100", "011101", "011110", "011111",
                "100000", "100001", "100010", "100011", "100100", "100101", "100110", "100111",
                "101000", "101001", "101010", "101011", "101100", "101101", "101110", "101111",
                "110000", "110001", "110010", "110011", "110100", "110101", "110110", "110111",
                "111000", "111001", "111010", "111011", "111100", "111101", "111110", "111111"
        };

        String[] chars = {
                "0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", ":", ";", "<", "=", ">", "?",
                "@", "A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O",
                "P", "Q", "R", "S", "T", "U", "V", "W",
                "`", "a", "b", "c", "d", "e", "f", "g",
                "h", "i", "j", "k", "l", "m", "n", "o",
                "p", "q", "r", "s", "t", "u", "v", "w"
        };

        for (int i = 0; i < bits.length; i++) {
            BIT_TO_CHAR.put(bits[i], chars[i].charAt(0));
            CHAR_TO_BIT.put(chars[i].charAt(0), bits[i]);
        }
    }
    private static RandomAis randomAis = new RandomAis();

    public static void main(String[] args) {
        String aisResult = generateAisResult();
        System.out.println(aisResult);
    }

    public static String generateAisResult() {
        while (true) {
            // RandomAis randomMessage = new RandomAis();
            // StringBuilder message = new StringBuilder();

            int MessageType = randomAis.generateRandomMessageType();
            int RepeatIndicator = randomAis.generateRandomRepeatIndicator();
            int MMSI = randomAis.generateRandomMMSI();
            int NavigationStatus = randomAis.generateRandomNavigationStatus();
            double ROT = randomAis.generateRandomTurnRate();
            double SOG = randomAis.generateRandomSpeed();
            int PositionAccuarcy = randomAis.generateRandomPositionAccuracy();
            double Lon = randomAis.generateRandomLongitude();
            double Lat = randomAis.generateRandomLatitude();
            double COG = randomAis.generateRandomCourse();
            int HDG = randomAis.generateRandomTrueHeading();
            int TimeStamp = randomAis.generateRandomTimeStamp();
            int MeneuverIndicator = randomAis.generateRandomManeuverIndicator();
            int Spare = randomAis.generateRandomSpare();
            int RaimFlag = randomAis.generateRandomRaimFlag();
            int SyncState = randomAis.generateRandomSyncState();
            int SlotTimeOut = randomAis.generateRandomSlotTimeOut();
            int SlotOffset = randomAis.generateRandomSlotOffset();


            // Message Type
            String messageType = String.format("%06d", Integer.parseInt(Integer.toBinaryString(MessageType)));
            System.out.println("messageType : " + messageType);

            // Repeat indicator
            String repeatIndicator = String.format("%02d", Integer.parseInt(Integer.toBinaryString(RepeatIndicator)));
            System.out.println("RepeatIndicator : " + repeatIndicator);

            // MMSI
            // MMSI를 2진수로 변환 후 30비트가 되지 않으면 30비트가 되도록 앞에 0을 추가
            String mMSI = String.format("%30s", Integer.toBinaryString(MMSI)).replace(' ', '0');
            System.out.println("MMSI : " + mMSI);

            // Navigational status
            String NavigationalStatus = String.format("%04d", Integer.parseInt(Integer.toBinaryString(NavigationStatus)));
            System.out.println("NavigationalStatus : " +  NavigationalStatus);

            // Rate of Turn (ROT)
            double x = ROT;
            double rotAis = Math.sqrt(Math.abs(x));
            double rot = 4.733 * rotAis;

            String rotInt = String.format("%.0f", rot);

            String rotBinary = String.format("%08d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(rotInt))));

            if (x == 128.0 || x == -128.0) {
                rotBinary = "10000000"; //기본값
            }
            if (x < 0) {
                //음수일 때 2의 보수 적용
                StringBuilder flippedBinary = new StringBuilder();
                for (int i = 0; i < rotBinary.length(); i++) {
                    flippedBinary.append(rotBinary.charAt(i) == '0' ? '1' : '0');
                }
                int onesComplementInt = Integer.parseInt(flippedBinary.toString(), 2);
                int twosComplementInt = onesComplementInt + 1;
                rotBinary = String.format("%8s", Integer.toBinaryString(twosComplementInt)).replace(' ', '0');
            }

            System.out.println("rot/rot(binary) : " + rot + "/" + rotBinary);


            //Speed Over Ground (SOG)
            double sogAis = SOG;
            double sog = 10 * sogAis;
            String sogInt = String.format("%.0f", sog);
            String sogBinary = String.format("%10s", Integer.toBinaryString(Integer.parseInt(sogInt))).replace(' ', '0');

            System.out.println("sog/sog(int)/sog(binary) : " + sogAis + "/" + sogInt+ "/" + sogBinary);

            // Position Accuracy
            String paBinary = String.format("%01d", Integer.parseInt(Integer.toBinaryString(PositionAccuarcy)));

            //System.out.println("position accuracy : " + paBinary);

            // 경도
            double degree = Lon;
            double lon = degree * 60;

            DecimalFormat df = new DecimalFormat("####.0000");
            String formattedLon = df.format(lon);

            String noDot = formattedLon.replace(".", "");

            BigInteger bigIntValue = new BigInteger(noDot);
            String lonBinary = String.format("%28s", bigIntValue.toString(2)).replace(' ', '0');

            System.out.println("lon/lon(binary) : " + formattedLon + "/" + lonBinary);

            // 위도
            double latDegree = Lat;
            double lat = latDegree * 60;

            DecimalFormat latDf = new DecimalFormat("####.0000");
            String formattedLat = latDf.format(lat);

            String latNoDot = formattedLat.replace(".", "");

            BigInteger latBigIntValue = new BigInteger(latNoDot);
            String latBinary = String.format("%27s", latBigIntValue.toString(2)).replace(' ', '0');

            System.out.println("lat/lat(binary) : " + formattedLat + "/" + latBinary);

            // Course Over Ground (COG)
            double cog = COG;
            int cogDecimal = (int) (cog * 10);

            Integer cogValue = new Integer(cogDecimal);
            String cogBinary = String.format("%12s", Integer.toBinaryString(cogValue)).replace(' ', '0');

            System.out.println("cog/cog(binary) : " + cog + "/" + cogBinary);

            // True Heading (HDG)
            String hdgStatus = String.format("%09d", Integer.parseInt(Integer.toBinaryString(HDG)));

            System.out.println("hdg : " + hdgStatus);

            // Time Stamp
            String tsStatus = String.format("%06d", Integer.parseInt(Integer.toBinaryString(TimeStamp)));

            System.out.println("time stamp : " + tsStatus);

            // Maneuver Indicator
            int mi = MeneuverIndicator;
            String miStatus = String.format("%02d", Integer.parseInt(Integer.toBinaryString(mi)));

            if (mi < 0 || mi > 4) {
                System.out.println("Maneuver Indicator : Not a valid Maneuver Indicator value");
            }

            // Spare
            String spare = String.format("%03d", Integer.parseInt(Integer.toBinaryString(Spare)));
            System.out.println("Spare : " + spare);

            // RAIM flag
            int raim = RaimFlag;
            String raimBinary = String.format("%01d", Integer.parseInt(Integer.toBinaryString(raim)));

            System.out.println("RAIM flag : " + raimBinary);

            // Radio status
            int syncState = SyncState;
            int slotTimeOut = SlotTimeOut;
            int slotOffset = SlotOffset;

            String syncStateBinary = String.format("%02d", Integer.parseInt(Integer.toBinaryString(syncState)));
            String slotTimeOutBinary = String.format("%03d", Integer.parseInt(Integer.toBinaryString(slotTimeOut)));
            String slotOffsetBinary = String.format("%14s", Integer.toBinaryString(slotOffset)).replace(' ', '0');

            System.out.println("Radio status : "+ syncStateBinary + slotTimeOutBinary + slotOffsetBinary);

            // 하나의 문자열로 결합
            String resultBinary = messageType + repeatIndicator + mMSI + NavigationalStatus + rotBinary + sogBinary + paBinary + lonBinary + latBinary + cogBinary + hdgStatus + tsStatus + miStatus + spare + raimBinary + syncStateBinary + slotTimeOutBinary + slotOffsetBinary;
            System.out.println("Binary result : " + resultBinary);

            String result2 = "!AIVDM,1,1,,A," + convertBitString(resultBinary) + ",0*";
            String checksum = calculateChecksum(result2);
            String AisResult = result2 + checksum;

            System.out.println(AisResult);

            return AisResult;
        }
    }



    // 6비트 문자열을 char로 변환하는 메서드
    public static char bitStringToChar(String bitString) {
        return BIT_TO_CHAR.getOrDefault(bitString, '?');
    }

    // 6비트씩 잘라서 변환하는 메서드
    public static String convertBitString(String bitString) {
        StringBuilder result = new StringBuilder();

        // 6비트씩 잘라서 처리
        for (int i = 0; i < bitString.length(); i += 6) {
            String subBitString = bitString.substring(i, Math.min(i + 6, bitString.length()));
            result.append(bitStringToChar(subBitString));
        }
        return result.toString();
    }

    // 체크섬 계산
    public static String calculateChecksum(String sentence) {

        String data = sentence.split("\\*")[0];

        int checksum = 0;
        for (int i = 1; i < data.length(); i++) {
            checksum ^= data.charAt(i);
        }
        return String.format("%02X", checksum);
    }

}