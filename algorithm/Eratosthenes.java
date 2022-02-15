import java.util.*;
public class Eratosthenes {
    public static void main(String[] args) {

        //範囲はランダムに決定
        Random rand = new Random();
        int num = rand.nextInt(10000);
        
        //ランダム値の表示
        System.out.println("Number:" + num);

        //素数判定の配列作成（True:合成数、False:素数）
        boolean[] notPrimes = new boolean [num+1];

        //０と１は素数ではないのでTrueに変更
        notPrimes[0] = notPrimes[1] = true;
        
        //For文で毎回計算しなくて済むように変数に平方根を格納
        double sqroot = Math.sqrt(num);
        
        //合成数の配列要素をTrueに変更
        for (int i = 2; i <= sqroot; i++){
            if(!notPrimes[i]){ 
                int num2 = num / i;
                for(int j = 2; j <= num2; j++){
                    notPrimes[i*j] = true;                        
                }
            }
        } 
        
        //素数および素数の個数を表示
        int cnt = 0;
        for(int i = 2; i <= num; i++ ){
            if(!notPrimes[i]){
                cnt++;
                System.out.print(i + ", ");
            }
        }
       System.out.println("\nPrime numbers:" + cnt );

    }
}