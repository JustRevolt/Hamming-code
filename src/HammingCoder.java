import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HammingCoder {
    private byte[] message;
    private byte[] hammingCode;
    private int nk, //минимально необходимое число контрольных символов
                ni, //необходимое число информационных символов
                Ni, //количество передаваемых сообщений
                n,  //длина сообщения
                b;

    private ArrayList[] checkerPosition;

    HammingCoder(int symbolsCount, byte[] message, int b) {
        ni = symbolsCount;
        this.message = message;
        this.b = b;
    }



    byte[] coding(){
        switch (b){
            case 3:
                codingB3();
                break;
            case 4:
                codingB3();
                codingB4();
                break;
            default: System.out.println("Не реализовано");
                    break;
        }
        return hammingCode;
    }

    private void codingB3(){
        Ni = (int) Math.pow(2,ni);
        nk = (int) Math.ceil(
                    lb(ni+1 +
                        Math.ceil(
                                lb(ni+1)
                        )
                    )
                );
        n = ni + nk;
        hammingCode = new byte[n];

        checkerPosition = new ArrayList[nk];

        for (int i=1; i<=nk; i++){
            ArrayList<Integer> K = new ArrayList<>();
            for (int x=1; x<=n; x++){
                if ( ((x - x % Math.pow(2,i-1)) % Math.pow(2,i)) == Math.pow(2,i-1) ) K.add(x-1);
            }
            hammingCode[K.get(0)] = -1;
            checkerPosition[i-1] = K;
        }

        int pointer = 0;
        for (byte infSymbol: message){
            while (hammingCode[pointer] == -1){
                ++pointer;
            }
            hammingCode[pointer] = infSymbol;
            pointer++;
        }


        for(int i = 0; i<nk; i++){
            int counter = 0;
            for (int j=1; j < checkerPosition[i].size(); j++){
                counter +=hammingCode[((int) checkerPosition[i].get(j))];
            }
            hammingCode[(int) checkerPosition[i].get(0)] = (byte) (counter%2);
        }
    }

    private void codingB4(){
        byte[] b4 =  new byte[n+1];
        int i = 0;
        int counter = 0;
        for (byte symbol: hammingCode ) {
            b4[i] = hammingCode[i++];
            counter += symbol;
        }
        b4[b4.length-1] = (byte) (counter%2);
        hammingCode = b4;
    }

    private double lb(double x){
        return Math.log(x)/Math.log(2);
    }
}
