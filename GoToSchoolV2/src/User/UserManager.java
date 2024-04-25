/**
 * This class contains all user information ( skill unlock, coin, experience,....
 * */

package User;

import java.io.*;

public class UserManager {

    private long coin;
    private short numberLeversUnlocked;
    private short numberSkillsUnlocked;

    public UserManager() {

    }
    public void readFile(String filePath) {
        try {
            InputStream input = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = br.readLine()) != null) {
                String[] token = line.split(";");
                coin = Long.parseLong(token[0]);
                numberSkillsUnlocked = Short.parseShort(token[1]);
                numberLeversUnlocked = Short.parseShort(token[2]);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveFile(String filePath) {
        try{
//            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            FileWriter fw = new FileWriter(filePath);
            fw.write(coin + ";" + numberSkillsUnlocked + ";" + numberLeversUnlocked);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred while saving the file: " + e.getMessage());
        }
    }
    public void resetAllData() {
        coin = 0;
        numberLeversUnlocked = 1;
        numberSkillsUnlocked = 1;
    }
    public long getCoin() {
        return coin;
    }

    public short getNumberLeversUnlocked() {
        return numberLeversUnlocked;
    }

    public short getNumberSkillsUnlocked() {
        return numberSkillsUnlocked;
    }
    public void setCoin(long coin) {
        this.coin = coin;
    }

    public void setNumberLeversUnlocked(short numberLeversUnlocked) {
        this.numberLeversUnlocked = numberLeversUnlocked;
    }
    public void setNumberSkillsUnlocked(short numberSkillsUnlocked) {
        this.numberSkillsUnlocked = numberSkillsUnlocked;
    }

    public short getMaxNumberLevers() {
        return (short) 5;
    }
}
