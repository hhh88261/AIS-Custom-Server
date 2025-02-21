# Ais Message 커스텀 서버


## 프로젝트 설명
ais 메시지를 랜덤으로 생성하여 출력하는 서버입니다.

## AIS 신호 생성과정
- 선박의 제원 정보 (MMSI 번호, 위.경도, 뱃머리 방향, 실제로 배가 향하고 있는 방향, 속도 등)
- 168비트 메시지와 424 비트 메시지로 구성되며 각 필드마다 다양한 비트로 나뉘어져 있습니다.(message ID: 6bits, message Length: 10bits)


# random.java
- 각 필드별로 랜덤한 난수를 생성하여 AIVDM 메시지를 만듭니다.


exmaple
- 최소 -90도, 최대 90도의 랜덤한 위도값을 설정합니다.
```
    public double generateRandomLatitude() {
        latitude += (random.nextDouble() - 0.5) * 0.02;
        latitude = Math.max(-90.0, Math.min(90.0, latitude));
        return latitude;
    }
```

- 선박의 속도를 설정합니다.
```
public double generateRandomSpeed() {
        return 100;
    }
```


# encode.java
- 생성된 난수를 AIS 신호로 인코딩
  

exmaple
- 메시지 타입을 6비트로 인코딩 합니다.(type : 1 --> 000001)
```
String messageType = String.format("%06d", Integer.parseInt(Integer.toBinaryString(MessageType)));
            System.out.println("messageType : " + messageType);  
```


- 선박의 회전율을 공식에 맞게 계산 후 8비트로 인코딩 합니다.
```
            // 회전율 / 4.733
            double x = ROT;
            double rotAis = Math.sqrt(Math.abs(x));
            double rot = 4.733 * rotAis;

            String rotInt = String.format("%.0f", rot);

            // 8비트로 구성
            String rotBinary = String.format("%08d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(rotInt))));

            // 회전율이 128, -128 일때 (128은 정보 없음을 뜻함)
            if (x == 128.0 || x == -128.0) {
                rotBinary = "10000000"; 
            }

            //회전율이 음수일 때 2의 보수 적용
            if (x < 0) {
                StringBuilder flippedBinary = new StringBuilder();
                for (int i = 0; i < rotBinary.length(); i++) {
                    flippedBinary.append(rotBinary.charAt(i) == '0' ? '1' : '0');
                }
                int onesComplementInt = Integer.parseInt(flippedBinary.toString(), 2);
                int twosComplementInt = onesComplementInt + 1;
                rotBinary = String.format("%8s", Integer.toBinaryString(twosComplementInt)).replace(' ', '0');
            }
```



#Main.java
- TCP 소켓 서버를 오픈합니다.
- 연결된 클라이언트에게 AIS 메시지를 송신합니다.
