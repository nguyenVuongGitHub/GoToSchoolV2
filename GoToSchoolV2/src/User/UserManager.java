/**
 * This class contains all user information ( skill unlock, coin, experience,....
 * */

package User;

import java.io.*;

public class UserManager {

    private long coin;
    private short numberLeversUnlocked;
    private short maxNumberLevers = 5;
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            // Write the data to the file in semicolon-separated format
            writer.write(coin + ";" + numberSkillsUnlocked + ";" + numberLeversUnlocked);
        } catch (IOException e) {
            e.printStackTrace();
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
        return maxNumberLevers;
    }
}
