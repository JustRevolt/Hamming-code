import java.util.Arrays;

public class Main {

    private static void printByteString(byte[] message){
        for (byte value: message){
            System.out.print(value);
        }
        System.out.println();
    }

    public static void main(String args[]) {
        byte[] message = {0,1,0,1,0,0,1,0};

        HammingCoder coder1 = new HammingCoder(message.length,message,3);
        byte[] hammingCode1 = coder1.coding();

        HammingCoder coder2 = new HammingCoder(message.length,message,4);
        byte[] hammingCode2 = coder2.coding();

//       Эмитация передачи с ошибкой в информ. байте
        hammingCode1[2] = (byte) ((hammingCode1[2]+1)%2);
        hammingCode2[2] = (byte) ((hammingCode2[2]+1)%2);

//       Эмитация передачи с ошибкой в контрол. байте b=3
//        hammingCode1[1] = (byte) ((hammingCode1[1]+1)%2);
//        hammingCode2[1] = (byte) ((hammingCode2[1]+1)%2);

//       Эмитация передачи с ошибкой в контрол. байте b=4
//        hammingCode2[hammingCode2.length-1] = (byte) ((hammingCode2[hammingCode2.length-1]+1)%2);



        HammingDecoder decoder1 = new HammingDecoder(hammingCode1.length, hammingCode1, 3);
        String message1 = decoder1.coding();
        byte[] resultMessage1 = decoder1.getResult();

        HammingDecoder decoder2 = new HammingDecoder(hammingCode2.length, hammingCode2, 4);
        String message2 = decoder2.coding();
        byte[] resultMessage2 = decoder2.getResult();

        System.out.print("Message for b=3"+"\n\t");
        printByteString(message);
        System.out.print("Hamming code for b=3"+"\n\t");
        printByteString(hammingCode1);
        System.out.print("Decoding for b=3"+"\n\t");
        System.out.print(message1+"\n\t");
        printByteString(resultMessage1);

        System.out.println("---------------------------------------------------");

        System.out.print("Message for b=4"+"\n\t");
        printByteString(message);
        System.out.print("Hamming code for b=4"+"\n\t");
        printByteString(hammingCode2);
        System.out.print("Decoding for b=4"+"\n\t");
        System.out.print(message2+"\n\t");
        printByteString(resultMessage2);
    }


}
