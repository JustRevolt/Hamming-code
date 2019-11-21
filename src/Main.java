import java.util.Arrays;

public class Main {

    private static void printByteString(byte[] message){
        String result = Arrays.toString(message);
        result = result.replace('[', (char) 0);
        result = result.replace(']', (char) 0);
        result = result.replace(',', (char) 0);
        result = result.replace(' ', (char) 0);
        System.out.println(result);
    }

    public static void main(String args[]) {
        byte[] message = {0,1,0,1};

        HammingCoder coder1 = new HammingCoder(4,message,3);
        byte[] hammingCode1 = coder1.coding();

        HammingCoder coder2 = new HammingCoder(hammingCode1.length,hammingCode1,4);
        byte[] hammingCode2 = coder2.coding();

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
        printByteString(hammingCode1);
        System.out.print("Hamming code for b=4"+"\n\t");
        printByteString(hammingCode2);
        System.out.print("Decoding for b=4"+"\n\t");
        System.out.print(message2+"\n\t");
        printByteString(resultMessage2);
    }


}
