import java.util.*;
import java.io.*;
public class Seq_BinSearch_Comparizon {
    public static void main(String[] args) {        
        
        //探索試行回数
        int epoch = 32; 
        int element = 3000;
        Random rand = new Random();

        //探索結果格納用配列
        //０：要素数、1：線形探索カウント、２：2分探索カウント、
        //３：Keyが格納されていた要素番号（なかった場合はー１）
        int[][] result = new int[element * epoch][４];        
        int[][] rslt_sum = new int[element][2]; 
        
        System.out.println("\n探索要素比較（==）の回数をカウント");

        for(int n = 0; n < epoch; n++){
            for(int i = 0; i < element; i++){
                System.out.println("\n**要素数" + (i+1) + "ｘ" + (n+1) + "回目の探索**");

                //配列要素数
                int num = i + 1;

                //探索する数（配列の大きさによりランダム値範囲変更）
                int key = rand.nextInt(5*(i+1));
        
                //昇順配列作成
                int[] list = new int[num];
                for (int j = 0; j < num; j++){
                    list[j] = rand.nextInt(5*(i+1));
                }
                Arrays.sort(list);

                //リスト出力
                // System.out.println("配列：");
                // for(int n: list){
                //     System.out.print(n + ",");
                // }
                System.out.println("\n要素数： " + num + ", 探索する数： " + key);

                //線形探索
                int cnt_for = seqFor(list, num, key );
                System.out.println("線形探索: " + cnt_for);

                //2分探索（比較回数とKeyが配列内にあるかを確認）        
                int[] bin_rslt = binSearch(list, num, key);
                int cnt_bin = bin_rslt[0];
                int check = bin_rslt[1];
                System.out.println("２分探索: " + cnt_bin);
                
                //Keyが配列にあるかどうか表示
                if(check == -1){
                    System.out.println("Number: " + key + " is not in the list.");
                } else {
                    System.out.println("Number: " + key + " is in the list[" + (check) + "].");
                }

                //結果を配列に格納
                rslt_sum[i][0] += cnt_for;
                rslt_sum[i][1] += cnt_bin;
                int[] resultOrder = {num, cnt_for, cnt_bin, check};
                for(int j = 0; j < resultOrder.length; j++){
                    result[n * element + i][j] = resultOrder[j];
                }
            }
        }
        double[][] rslt_avg = new double[element][2];
        for(int i = 0; i < rslt_sum.length; i++){
            for(int j = 0; j < 2; j++){
                rslt_avg[i][j] = rslt_sum[i][j] / 1.0/ epoch;
                }

        }

        //CSV形式で出力
        exportCsv(result);
        exportAvg(rslt_avg);
        
    }


    //線形探索
    static int seqFor(int[] list, int num, int key){
        int cnt = 0; 
        for(int i = 0; i < num; i++){
            cnt++;
            if(list[i] == key){
                break;
            }
        }   
        return cnt;
      }

    //2分探索
    static int[] binSearch(int[] list, int num, int key){

        int pl = 0;
        int pr = num-1;
        int cnt = 0;

        while(pl <= pr){        
            int pc = (pl + pr) / 2; 
            
            cnt++;
            if(list[pc] == key){
                //配列の値が重複していた場合、最初の要素値を取り出す
                cnt++;
                while(pc > 0 && list[pc] == list[pc - 1]){
                    pc--;
                    cnt++;
                }
                int[] result = {cnt, pc};
                return result;

            } else if(list[pc] < key){
                pl = pc +1;
            } else{
                pr = pc-1;
            }             
        }
        int[] result = {cnt, -1};
        return result;
    }

    //CSV出力
    static void exportCsv(int[][] resultList){
         // 出力ファイルの作成
        try(FileWriter f = new FileWriter("./result.csv", false); PrintWriter p = new PrintWriter(new BufferedWriter(f))) {   
            // ヘッダーを指定する
            p.print("#_elements,");
            p.print("Cnt_SeqSearch,");
            p.print("Cnt_BinSearch,");
            p.print("Key_list[]");
            p.println();
 
            // 内容をセットする
            for(int i = 0; i < resultList.length; i++){
                for(int j = 0; j < resultList[i].length; j++){
                    p.print(resultList[i][j]);
                    p.print(",");
                }
                p.println();    // 改行
            }

            System.out.println("\nCSVファイル出力完了！");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
         
    }

    //CSV出力
    static void exportAvg(double[][] rslt_avg){
        // 出力ファイルの作成
       try(FileWriter f = new FileWriter("./result_avg.csv", false); PrintWriter p = new PrintWriter(new BufferedWriter(f))) {   
           // ヘッダーを指定する
           p.print("#_elements,");
           p.print("Avg_SeqSearch,");
           p.print("Avg_BinSearch,");
           p.println();

           // 内容をセットする
           for(int i = 0; i < rslt_avg.length; i++){
            p.print(i+1 + ",");
               for(int j = 0; j < rslt_avg[i].length; j++){
                   p.print(rslt_avg[i][j] + ",");                   
               }
               p.println();    // 改行
           }

           System.out.println("\nCSV_avgファイル出力完了！");

       } catch (IOException ex) {
           ex.printStackTrace();
       }
        
   }    
}
