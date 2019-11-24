import java.util.ArrayList;

class HammingDecoder {
    private byte [] message;
    private byte[] hammingCode;
    private int nk, //минимально необходимое число контрольных символов
            ni, //необходимое число информационных символов
            Ni, //количество передаваемых сообщений
            n,  //длина сообщения
            b;

    private int errorByte;

    private ArrayList[] checkerPosition;

    byte [] getResult(){
        return message;
    }

    HammingDecoder(int symbolsCount, byte[] hammingCode, int b) {
        n = symbolsCount;
        this.hammingCode = hammingCode;
        this.b = b;
    }

    String coding(){
        String result;
        switch (b){
            case 3:
                if (decodingB3(hammingCode)) result = ("Передано с ошибкой в символе "+errorByte);
                else result = ("Передано без ошибок");
                break;
            case 4:
                boolean b4 = decodingB4(hammingCode);
                boolean b3 = decodingB3(message);

                if (b3){
                    if (b4){
                        result = ("Передано с ошибкой в символе "+errorByte);
                    }else {
                        result = ("Передано с двойной ошибкой");
                        message = new byte[0];
                    }
                }else result = ("Передано без ошибок");

                break;

            default: result = "Не реализовано";
        }
        return result;
    }

    private boolean decodingB3(byte[] hammingCodeB3){

        nk = (int) Math.ceil(lb(n+1 ));
        ni = n - nk;
        message = new byte [ni];
        checkerPosition = new ArrayList[nk];
        byte [] syndrome = new byte [nk];
        Byte[] hammingB3 = new Byte[hammingCodeB3.length];

        for (int i=1; i<=nk; i++){
            ArrayList<Integer> K = new ArrayList<>();
            for (int x=1; x<=n; x++){
                hammingB3[x-1] = hammingCodeB3[x-1];
                if ( ((x - x % Math.pow(2,i-1)) % Math.pow(2,i)) == Math.pow(2,i-1) ) K.add(x-1);
            }
            checkerPosition[i-1] = K;
        }

        for (int i=0; i<nk; i++){
            int counter = 0;
            for (Object symbol:checkerPosition[i] ) {
                counter += hammingB3[(int) symbol];
            }
            syndrome[i] = (byte) (counter % 2);
        }

        errorByte = binaryToDecimal(syndrome);

        boolean error = false;
        if ( errorByte > 0) {
            hammingB3[errorByte-1] = (byte) ((hammingB3[errorByte - 1] + 1) % 2);
            error = true;
        }

        int pointer = 0;
        int pointerK = 0;
        for (int i = 0; i<hammingB3.length; i++){
            if (pointerK < nk && checkerPosition[pointerK].get(0).equals(i)){
                pointerK++;
            } else{
                message[pointer] = hammingB3[i];
                pointer++;
            }
        }

        return error;
    }

    private boolean decodingB4(byte [] hammingB4){
        boolean error = false;
        int counter = 0;
        for(byte  symbol: hammingB4){
            counter += symbol;
        }
        if (counter%2 == 1){
            error = true;
        }

        message = new byte [n-1];
        System.arraycopy(hammingB4, 0, message, 0, n - 1);
        n--;

        return error;
    }


    private int binaryToDecimal(byte [] x){
        int result = 0;
        for (int i = 0; i<x.length; i++){
            result += x[i]*Math.pow(2,i);
        }
        return result;
    }

    private double lb(double x){
        return Math.log(x)/Math.log(2);
    }
}
